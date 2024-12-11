package com.unicore.classroom_service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unicore.classroom_service.dto.request.ClassroomBulkCreationRequest;
import com.unicore.classroom_service.dto.request.ClassroomCreationRequest;
import com.unicore.classroom_service.dto.request.GeneralTestBulkCreationRequest;
import com.unicore.classroom_service.dto.request.GeneralTestCreationRequest;
import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.request.StudentGroupingCreationRequest;
import com.unicore.classroom_service.dto.request.StudentListCreationRequest;
import com.unicore.classroom_service.dto.response.ClassroomResponse;
import com.unicore.classroom_service.entity.Classroom;
import com.unicore.classroom_service.entity.Group;
import com.unicore.classroom_service.entity.StudentInGroup;
import com.unicore.classroom_service.entity.Subclass;
import com.unicore.classroom_service.enums.ClassType;
import com.unicore.classroom_service.enums.ExamFormat;
import com.unicore.classroom_service.enums.WeightType;
import com.unicore.classroom_service.exception.AppException;
import com.unicore.classroom_service.exception.ErrorCode;
import com.unicore.classroom_service.mapper.ClassroomMapper;
import com.unicore.classroom_service.repository.ClassroomRepository;
import com.unicore.classroom_service.repository.httpclient.ClassEventClient;
import com.unicore.classroom_service.repository.httpclient.OrganizationClient;

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
    private final OrganizationClient organizationClient;

    public Mono<ClassroomResponse> createClassroom(Classroom classroom) {
        return organizationClient.getSubject(classroom.getSubjectCode())
            .map(subject -> {
                log.info(subject.toString());
                classroom.setSubjectMetadata(subject.getMetadata());
                return classroom;
            })
            .flatMap(newClass -> checkDuplicate(newClass.getCode(), newClass.getSemester(), newClass.getYear()))
            .flatMap(result -> {
                log.info(classroom.toString());
                return Boolean.TRUE.equals(result) ? 
                    Mono.error(new AppException(ErrorCode.DUPLICATE)) :
                    saveClassroom(classroom);
            }
            );
    }

    private Mono<ClassroomResponse> saveClassroom(Classroom classroom) {
        return Mono.just(classroom)
            .flatMap(classroomRepository::save)
            .map(classroomMapper::toClassroomResponse)
            .onErrorResume(throwable -> {
                if (throwable instanceof DataAccessException) {
                    log.error("R2DBC data invalidation error: {}", throwable.getCause());
                    // Return an error response or rethrow as a custom exception
                    return Mono.error(new AppException(ErrorCode.DUPLICATE));
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
        List<Classroom> classes = new ArrayList<>();
        List<ClassroomCreationRequest> classRequests = request.getClasses();
        String currentClassCode = classRequests.get(0).getCode();
        Set<Subclass> subclasses = new HashSet<>();
        for (var i = 0; i < classRequests.size(); i++) {
            ClassroomCreationRequest currentSubclass = classRequests.get(i);
            subclasses.add(classroomMapper.toSubclass(currentSubclass));
            if (i + 1 < classRequests.size()) {
                ClassroomCreationRequest nextSubclass = classRequests.get(i + 1);
                if (!nextSubclass.getCode().startsWith(currentClassCode)) {
                    classes.add(buildClassroom(currentClassCode, subclasses, currentSubclass, request.getOrganizationId()));
                    subclasses = new HashSet<>();
                    currentClassCode = nextSubclass.getCode();
                }
            } else {
                classes.add(buildClassroom(currentClassCode, subclasses, currentSubclass, request.getOrganizationId()));
            }
        }
        return Flux.fromIterable(classes)
            .flatMap(this::createClassroom)
            .collectList() // Collect all ClassroomResponse objects
            .flatMap(classroomResponses -> {
                log.info("AYOOOO" + classroomResponses.toString());
                List<GeneralTestCreationRequest> requests = new ArrayList<>();
                for (ClassroomResponse response : classroomResponses) {
                    for (Subclass subclass : response.getSubclasses()) {
                        if (subclass.getType().isMainClass()) {
                            if (response.getSubjectMetadata().getMidtermWeight() > 0) {
                                requests.add(new GeneralTestCreationRequest(
                                    response.getId(),
                                    subclass.getCode(),
                                    WeightType.MIDTERM,
                                    response.getSubjectMetadata().getMidtermFormat()
                                ));
                            }
                            requests.add(new GeneralTestCreationRequest(
                                response.getId(),
                                subclass.getCode(),
                                WeightType.FINAL_TERM,
                                response.getSubjectMetadata().getFinalFormat()
                            ));
                        } else {
                            requests.add(new GeneralTestCreationRequest(
                                response.getId(),
                                subclass.getCode(),
                                WeightType.PRACTICAL,
                                response.getSubjectMetadata().getPracticalFormat()
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
            .flatMapMany(Flux::fromIterable);
    }
    
    private Classroom buildClassroom(String code, Set<Subclass> subclasses, ClassroomCreationRequest classRequest, String organizationId) {
        return Classroom.builder()
            .code(code)
            .subclasses(List.copyOf(subclasses))
            .subjectCode(classRequest.getSubjectCode())
            .orgManaged(classRequest.isOrgManaged())
            .organizationId(organizationId)
            .year(classRequest.getYear())
            .semester(classRequest.getSemester())
            .build();
    }

    public Mono<ClassroomResponse> updateClassSize(StudentListCreationRequest request) {
        return classroomRepository.findById(request.getClassId())
            .map(classroom -> {
                for (Subclass subclass : classroom.getSubclasses()) {
                    if (subclass.getCode().equals(request.getSubclassCode())) {
                        subclass.setCurrentSize(request.getStudentCodes().size());
                        break;
                    }
                }
                return classroomMapper.toClassroomResponse(classroom);
            });
    }

    public Mono<ClassroomResponse> getClassroomById(String id) {
        return classroomRepository.findById(id)
            .map(classroomMapper::toClassroomResponse);
    }

    public Mono<ClassroomResponse> getClassroomByCode(String code) {
        return classroomRepository.findByCode(code)
            .map(classroomMapper::toClassroomResponse);
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

    public Mono<ClassroomResponse> createSubclassFromGrouping(StudentGroupingCreationRequest request) {
        if (!request.isCreateSubclass()) return Mono.empty();
        return Mono.just(request)
            .flatMap(groupRequest -> getClassroomById(groupRequest.getClassId()))
            .map(classroom -> {
                Subclass mainSubclass = classroom.getSubclasses().getFirst();
                String classCode = classroom.getCode();
                List<Group> groups = request.getGroups();
                Map<String, Subclass> mapTeacherSubclasses = new HashMap<>();
                Map<String, StudentListCreationRequest> mapTeacherList = new HashMap<>();
                for (int i = 0; i < groups.size(); i++) {
                    Group group = groups.get(i);
                    if (mapTeacherSubclasses.containsKey(group.getTeacherCode())) {
                        Set<String> studentCodes = Set.copyOf(mapTeacherList.get(group.getTeacherCode()).getStudentCodes());
                        studentCodes.addAll(group.getMembers().stream()
                            .map(StudentInGroup::getStudentCode).toList());
                        mapTeacherList.get(group.getTeacherCode()).setStudentCodes(studentCodes);
                        mapTeacherSubclasses.get(group.getTeacherCode()).setCurrentSize(studentCodes.size());
                    } else {
                        String subclassCode = group.getName().isEmpty() ? 
                            classCode + ".HD" + (i + 1) :
                            classCode + "." + group.getName().replace(" ", "_");
                        Subclass subclass = Subclass.builder()
                            .code(subclassCode)
                            .teacherCode(group.getTeacherCode())
                            .startDate(mainSubclass.getStartDate())
                            .endDate(mainSubclass.getEndDate())
                            .maxSize(mainSubclass.getCurrentSize())
                            .currentSize(group.getMembers().size())
                            .type(ClassType.NHOM_HUONG_DAN)
                            .note("Nhóm hướng dẫn " + subclassCode)
                            .build();
                        mapTeacherSubclasses.put(group.getTeacherCode(), subclass);
                        mapTeacherList.put(
                            group.getTeacherCode(), 
                            new StudentListCreationRequest(
                                classroom.getId(),
                                subclassCode,
                                null,
                                Set.copyOf(
                                    group.getMembers().stream()
                                    .map(StudentInGroup::getStudentCode).toList()
                                ),
                                List.of()
                            )
                        );
                    }
                }
                studentListService.createStudentListBulk(List.copyOf(mapTeacherList.values()));
                
                classroom.setSubclasses(List.copyOf(mapTeacherSubclasses.values()));
                return classroom;
            })
            .map(classroomMapper::toClassroom)
            .flatMap(classroomRepository::save)
            .map(classroomMapper::toClassroomResponse)
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));


            //// Xét trường hợp sv ở lớp khác, lưu sv vào foreign_students
    }
}
