package com.unicore.classroom_service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unicore.classroom_service.dto.request.CheckStudentClassForGroupingRequest;
import com.unicore.classroom_service.dto.request.ClassFilterRequest;
import com.unicore.classroom_service.dto.request.ClassroomBulkCreationRequest;
import com.unicore.classroom_service.dto.request.ClassroomCreationRequest;
import com.unicore.classroom_service.dto.request.GeneralTestBulkCreationRequest;
import com.unicore.classroom_service.dto.request.GeneralTestCreationRequest;
import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.request.GetClassBySemesterAndYearRequest;
import com.unicore.classroom_service.dto.request.GetSubjectByYearAndSemesterRequest;
import com.unicore.classroom_service.dto.request.UpdateClassGroupingRequest;
import com.unicore.classroom_service.dto.request.UpdateClassImportStatusRequest;
import com.unicore.classroom_service.dto.response.ClassroomFilterResponse;
import com.unicore.classroom_service.dto.response.ClassroomResponse;
import com.unicore.classroom_service.dto.response.Group;
import com.unicore.classroom_service.dto.response.StudentForGroupingResponse;
import com.unicore.classroom_service.dto.response.SubjectNotExistsError;
import com.unicore.classroom_service.dto.response.SubjectResponse;
import com.unicore.classroom_service.dto.response.TeacherNotExistsError;
import com.unicore.classroom_service.dto.response.TeacherResponse;
import com.unicore.classroom_service.entity.Classroom;
import com.unicore.classroom_service.entity.Subclass;
import com.unicore.classroom_service.entity.SubjectMetadata;
import com.unicore.classroom_service.enums.ClassType;
import com.unicore.classroom_service.enums.WeightType;
import com.unicore.classroom_service.exception.AppException;
import com.unicore.classroom_service.exception.ErrorCode;
import com.unicore.classroom_service.mapper.ClassroomMapper;
import com.unicore.classroom_service.repository.ClassroomRepository;
import com.unicore.classroom_service.repository.httpclient.ClassEventClient;
import com.unicore.classroom_service.repository.httpclient.OrganizationClient;
import com.unicore.classroom_service.repository.httpclient.ProfileClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;

    private final StudentListService studentListService;

    private final ClassEventClient classEventClient;
    private final ProfileClient profileClient;
    private final OrganizationClient organizationClient;

    public Mono<ClassroomResponse> createClassroom(Classroom classroom, SubjectResponse subject) {
        SubjectMetadata subjectMetadata = subject.toMetadata();
        classroom.setSubject(subjectMetadata);
        return saveClassroom(classroom);
    }

    private Mono<Map<String, SubjectResponse>> getSubjects(int semester, int year) {
        return organizationClient.getSubjectsBySemesterAndYear(new GetSubjectByYearAndSemesterRequest(semester, year))
            .map(subjects -> {
                log.info("getSubjects: " + subjects.toString());
                Map<String, SubjectResponse> map = new HashMap<>();
                for (SubjectResponse subject : subjects) {
                    map.put(subject.getCode(), subject);
                }
                return map;
            });
    }

    private Mono<Map<String, TeacherResponse>> getTeachers(List<String> codes) {
        return profileClient.getTeachersByCodes(codes)
            .map(teachers -> {
                log.info("getteachers: " + teachers.toString());
                Map<String, TeacherResponse> map = new HashMap<>();
                for (TeacherResponse teacher : teachers) {
                    map.put(teacher.getCode(), teacher);
                }
                return map;
            });
    }

    private Mono<ClassroomResponse> saveClassroom(Classroom classroom) {
        return Mono.just(classroom)
            .flatMap(classroomRepository::save)
            .map(classroomMapper::toClassroomResponse)
            .onErrorResume(throwable -> {
                if (throwable instanceof DataAccessException) {
                    log.error("R2DBC data invalidation error: {}", throwable.getCause());
                    // Return an error response or rethrow as a custom exception
                    return Mono.error(new AppException(ErrorCode.UNCATEGORIZED));
                }
                return Mono.error(throwable); // Propagate other exceptions
            })
            .doOnSuccess(response -> {
                // goi kafka tao lop, event
            });
    }

    @Transactional
    public Flux<ClassroomResponse> createClassrooms(ClassroomBulkCreationRequest request) {
        if (request.getClasses().isEmpty()) return Flux.empty();
        ConcurrentLinkedQueue<SubjectNotExistsError> failedSubjects = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<TeacherNotExistsError> failedTeachers = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<String> duplicatedClasses = new ConcurrentLinkedQueue<>();
        List<Classroom> classes = new ArrayList<>();

        List<ClassroomCreationRequest> classRequests = request.getClasses();
        String currentClassCode = classRequests.get(0).getCode();
        Set<Subclass> subclasses = new HashSet<>();

        for (var i = 0; i < classRequests.size(); i++) {
            Subclass currentSubclass = classroomMapper.toSubclass(classRequests.get(i));
            if (!classRequests.get(i).isOrgManaged()) {
                currentSubclass.setMainTeacherCode(currentSubclass.getTeacherCodes().getFirst());
            }
            subclasses.add(currentSubclass);
            if (i + 1 < classRequests.size()) {
                ClassroomCreationRequest nextSubclass = classRequests.get(i + 1);
                if (!nextSubclass.getCode().startsWith(currentClassCode)) {
                    classes.add(buildClassroom(currentClassCode, subclasses, classRequests.get(i), request.getOrganizationId()));
                    subclasses = new HashSet<>();
                    currentClassCode = nextSubclass.getCode();
                }
            } else {
                classes.add(buildClassroom(currentClassCode, subclasses, classRequests.get(i), request.getOrganizationId()));
            }
        }

        
        List<String> teacherCodes = new ArrayList<>();
        for (ClassroomCreationRequest classroom : request.getClasses()) {
            teacherCodes.addAll(classroom.getTeacherCodes());
        }

        return getTeachers(teacherCodes)
            .map(teachers -> {
                for (ClassroomCreationRequest classRequest : request.getClasses()) {
                    List<String> codes = classRequest.getTeacherCodes();
                    List<String> names = classRequest.getTeacherNames();
                    for (int i = 0; i < codes.size(); i++) {
                        if (codes.get(i) != null && !codes.get(i).isEmpty() && !teachers.containsKey(codes.get(i))) {
                            failedTeachers.add(new TeacherNotExistsError(
                                classRequest.getCode(), 
                                codes.get(i), 
                                names.get(i))
                            );
                        }
                    }
                }
                return teachers;
            })
            .flatMap(teachers -> getSubjects(request.getSemester(), request.getYear()))
            .flatMapMany(subjects ->  {
                log.info("createClassrooms 1: " + subjects.toString());
                return Flux.fromIterable(classes)
                .flatMap(classroom -> {
                    if (!subjects.containsKey(classroom.getSubjectCode())) {
                        failedSubjects.add(new SubjectNotExistsError(
                            classroom.getCode(), 
                            classroom.getSubjectCode(), 
                            classroom.getSubjectName()));
                        return Mono.empty();
                    }
                    return Mono.just(classroom);
                })
                .flatMap(classroom -> {
                    return checkDuplicate(classroom.getCode(), classroom.getSemester(), classroom.getYear())
                        .flatMap(result -> {
                            if (Boolean.TRUE.equals(result)) {
                                duplicatedClasses.add(classroom.getCode());
                                return Mono.empty();
                            }
                            return Mono.just(classroom);
                        });
                })
                .collectList()
                .flatMap(classrooms -> {
                    if (!duplicatedClasses.isEmpty() || !failedSubjects.isEmpty() || !failedTeachers.isEmpty()) {
                        return Mono.error(new AppException(
                            ErrorCode.FAILED_CLASS_CREATION,
                            Map.<String, Object>of(
                                "subject_not_found", new ArrayList<>(failedSubjects),
                                "teacher_not_found", new ArrayList<>(failedTeachers),
                                "duplicated", new ArrayList<>(duplicatedClasses)
                            )
                        ));
                    }
                    return Mono.just(classrooms);
                })
                .flatMapMany(Flux::fromIterable)
                .flatMap(classroom -> {
                    if (subjects.containsKey(classroom.getSubjectCode())) {
                        return createClassroom(classroom, subjects.get(classroom.getSubjectCode()))
                            .onErrorResume(AppException.class, e -> {
                                if (e.getErrorCode() == ErrorCode.DUPLICATE) {
                                    duplicatedClasses.add(classroom.getCode());
                                }
                                return Mono.empty();
                            });
                    }
                    return Mono.empty();
                });
            })
            .collectList() // Collect all ClassroomResponse objects
            .flatMap(classroomResponses -> {
                if (!duplicatedClasses.isEmpty() || !failedSubjects.isEmpty()) {
                    return Mono.error(new AppException(
                        ErrorCode.FAILED_CLASS_CREATION,
                        Map.<String, Object>of(
                            "subject_not_found", new ArrayList<>(failedSubjects),
                            "duplicated", new ArrayList<>(duplicatedClasses)
                        )
                    ));
                }
                log.info("AYOOOO" + classroomResponses.toString());
                List<GeneralTestCreationRequest> requests = new ArrayList<>();
                for (ClassroomResponse response : classroomResponses) {
                    for (Subclass subclass : response.getSubclasses()) {
                        if (subclass.getType().isMainClass()) {
                            if (response.getSubject().getMidtermWeight() > 0) {
                                requests.add(new GeneralTestCreationRequest(
                                    response.getId(),
                                    response.getType(),
                                    subclass.getCode(),
                                    WeightType.MIDTERM,
                                    (float) response.getSubject().getMidtermWeight(), 
                                    response.getSubject().getMidtermFormat()
                                ));
                            }
                            requests.add(new GeneralTestCreationRequest(
                                response.getId(),
                                response.getType(),
                                subclass.getCode(),
                                WeightType.FINAL_TERM,
                                (float) response.getSubject().getFinalWeight(), 
                                response.getSubject().getFinalFormat()
                            ));
                        } else {
                            requests.add(new GeneralTestCreationRequest(
                                response.getId(),
                                response.getType(),
                                subclass.getCode(),
                                WeightType.PRACTICAL,
                                (float) response.getSubject().getPracticalWeight(), 
                                response.getSubject().getPracticalFormat()
                            ));
                        }
                    }
                }
                log.info("Hiiii" + requests.toString());
                GeneralTestBulkCreationRequest bulkRequest = new GeneralTestBulkCreationRequest(List.copyOf(requests));

                return classEventClient.createBulk(bulkRequest)
                        .doOnSuccess(response -> log.info("Success: {}", response))
                        .doOnError(error -> log.error("Error during createBulk", error))
                        .then(Mono.just(classroomResponses)); // Pass classroomResponses downstream
            })
            .flatMapMany(Flux::fromIterable)
            .switchIfEmpty(Mono.error(new AppException(
                ErrorCode.FAILED_CLASS_CREATION,
                new ArrayList<>(duplicatedClasses)
            )));
    }
    
    private Classroom buildClassroom(String code, Set<Subclass> subclasses, ClassroomCreationRequest classRequest, String organizationId) {
        ClassType classType = ClassType.LOP_THUONG;
        if (!subclasses.isEmpty()) {
            for (Subclass subclass : subclasses) {
                if (subclass.getType().isMainClass()) {
                    switch (subclass.getType()) {
                        case ClassType.DO_AN:
                            classType = ClassType.DO_AN;
                            break;
                        case ClassType.KHOA_LUAN:
                            classType = ClassType.KHOA_LUAN;
                            break;
                        case ClassType.THUC_TAP:
                            classType = ClassType.THUC_TAP;
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }
        }
        return Classroom.builder()
            .code(code)
            .type(classType)
            .subclasses(List.copyOf(subclasses))
            .subjectCode(classRequest.getSubjectCode())
            .orgManaged(classRequest.isOrgManaged())
            .organizationId(organizationId)
            .year(classRequest.getYear())
            .semester(classRequest.getSemester())
            .build();
    }

    public Mono<ClassroomResponse> getClassroomById(String id) {
        return classroomRepository.findById(id)
            .map(classroomMapper::toClassroomResponse);
    }

    public Mono<ClassroomResponse> getClassroomByCodeSemesterYear(GetClassBySemesterAndYearRequest request) {
        return classroomRepository.findByCodeAndSemesterAndYear(
                request.getClassCode(),
                request.getSemester(),
                request.getYear()
            )
            .map(classroomMapper::toClassroomResponse)
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    public Mono<ClassroomResponse> updateClassImportStatus(UpdateClassImportStatusRequest request) {
        return classroomRepository.findByCodeAndSemesterAndYear(
                request.getClassCode(),
                request.getSemester(),
                request.getYear()
            )
            .flatMap(classroom -> {
                for (Subclass subclass : classroom.getSubclasses()) {
                    if (subclass.getCode().equals(request.getClassCode())) {
                        subclass.setCouncilImported(request.isCouncilImported());
                        subclass.setFinalImported(request.isFinalImported());
                        subclass.setMidtermImported(request.isMidtermImported());
                        break;
                    }
                }
                return classroomRepository.save(classroom);
            })
            .map(classroomMapper::toClassroomResponse)
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    public Flux<ClassroomResponse> getClassrooms() {
        return classroomRepository.findAll()
            .map(classroomMapper::toClassroomResponse);
    }

    public Flux<ClassroomResponse> getClassroomsByOrganizationId(String organizationId) {
        return classroomRepository.findByOrganizationId(organizationId)
            .map(classroomMapper::toClassroomResponse);
    }

    public Mono<Void> deleteById(String id) {
        return classroomRepository.deleteById(id);
    }

    public Mono<Void> deleteByIds(List<String> ids) {
        return classroomRepository.deleteAllById(ids);
    }

    private Mono<Boolean> checkDuplicate(String code, int semester, int year) {
        return classroomRepository.findByCodeAndSemesterAndYear(code, semester, year)
            .hasElement();
    }

    public Flux<ClassroomResponse> filterClassrooms(ClassFilterRequest filterRequest) {
        return classroomRepository.findByFilters(filterRequest)
            .map(classroomMapper::toClassroomResponse);
    }

    public Mono<ClassroomFilterResponse> filterClassroomsAndOtherStuff(ClassFilterRequest filterRequest) {
        ClassroomFilterResponse result = new ClassroomFilterResponse();
        return classroomRepository.findByFilters(filterRequest)
            .map(classroom -> {
                result.getClasses().add(classroomMapper.toClassroomResponse(classroom));
                for (Subclass subclass : classroom.getSubclasses()) {
                    log.info(subclass.toString());
                    GetByClassRequest classInfo = new GetByClassRequest(classroom.getId(), subclass.getCode());
                    if (!subclass.isStudentImported())
                        result.getNoStudentLists().add(classInfo);

                    if (subclass.getType() == ClassType.LY_THUYET) {
                        if (classroom.getSubject().getMidtermWeight() > 0 && !subclass.isMidtermImported())
                            result.getNoMidterm().add(classInfo);
                        if (!subclass.isFinalImported())
                            result.getNoFinal().add(classInfo);
                    }
                    else if (subclass.getType() == ClassType.THUC_TAP && !subclass.isCouncilImported())
                        result.getNoInternCouncil().add(classInfo);
                    else if (subclass.getType() == ClassType.KHOA_LUAN && !subclass.isCouncilImported())
                        result.getNoThesisCouncil().add(classInfo);
                }
                return classroom;
            })
            .collectList()
            .map(classes -> result);
    }

    public Mono<ClassroomResponse> updateClassGroupingId(UpdateClassGroupingRequest request) {
        return classroomRepository.findById(request.getClassId())
            .map(response -> {
                for (Subclass subclass : response.getSubclasses()) {
                    if (subclass.getCode().equals(request.getSubclassCode())) {
                        subclass.setGroupingId(request.getGroupingId());
                        break;
                    }
                }
                return response;
            })
            .flatMap(classroomRepository::save)
            .map(classroomMapper::toClassroomResponse);
    }

    public Mono<StudentForGroupingResponse> checkStudentClassForGrouping(CheckStudentClassForGroupingRequest request) {
        return classroomRepository.findById(request.getClassId())
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.CLASS_NOT_FOUND)))
            .flatMap(classroom -> studentListService.getStudentList(new GetByClassRequest(classroom.getId(), request.getSubclassCode()))
                .flatMap(studentList -> {
                    log.info("1 " + studentList.toString());
                    if (studentList.getStudentCodes().contains(request.getStudentCode())) {
                        log.info("2 ");
                        return profileClient.getStudentByCode(request.getStudentCode())
                            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)))
                            .map(student -> {
                                StudentForGroupingResponse response = classroomMapper.toForGroupingResponse(student);
                                response.setValid(true);
                                response.setClassId(classroom.getId());
                                response.setSubclassCode(request.getSubclassCode());
                                return response;
                            });
                    } else {
                        log.info("3 ");
                        return studentListService.getStudentClasses(request.getStudentCode())
                            .flatMap(classes -> profileClient.getStudentByCode(request.getStudentCode())
                                .map(student -> {
                                    log.info("4 ");
                                    StudentForGroupingResponse response = classroomMapper.toForGroupingResponse(student);
                                    Subclass idealClass = null;
                                    String classId = "";

                                    for (ClassroomResponse tempClass : classes) {
                                        if (tempClass.getSubjectCode().equals(classroom.getSubjectCode())) {
                                            for (Subclass tempSubclass : tempClass.getSubclasses()) {
                                                if (tempSubclass.getTeacherCodes().contains(request.getTeacherCode())) {
                                                    idealClass = tempSubclass;
                                                    classId = tempClass.getId();
                                                }
                                            }
                                        }
                                    }

                                    if (idealClass == null) {
                                        response.setValid(false);
                                        response.setReasonForInvalid("No suitable classes");
                                        return response;
                                    } else {
                                        response.setValid(true);
                                        response.setClassId(classId);
                                        response.setSubclassCode(idealClass.getCode());
                                        return response;
                                    }
                                })
                        );
                    }
                }
            )
            .flatMap(response ->  classEventClient.getEventGrouping(request.getProjectId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)))
                .map(grouping -> {
                    for (Group group : grouping.getGroups()) {
                        if (group.hasMember(response.getCode())) {
                            response.setValid(false);
                            response.setReasonForInvalid("Student already belong to group " + group.getIndex());
                            return response;
                        }
                    }
                    return response;
                })
            )

        );
    }
}
