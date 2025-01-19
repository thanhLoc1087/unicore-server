package com.unicore.classroom_service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.unicore.classroom_service.dto.request.AddForeignStudentsRequest;
import com.unicore.classroom_service.dto.request.AddStudentsToListRequest;
import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.request.InternStudentListImportRequest;
import com.unicore.classroom_service.dto.request.InternStudentRequest;
import com.unicore.classroom_service.dto.request.StudentListCreationRequest;
import com.unicore.classroom_service.dto.response.ClassroomResponse;
import com.unicore.classroom_service.dto.response.StudentListResponse;
import com.unicore.classroom_service.dto.response.TeacherResponse;
import com.unicore.classroom_service.entity.Classroom;
import com.unicore.classroom_service.entity.StudentList;
import com.unicore.classroom_service.entity.Subclass;
import com.unicore.classroom_service.enums.ClassType;
import com.unicore.classroom_service.exception.AppException;
import com.unicore.classroom_service.exception.ErrorCode;
import com.unicore.classroom_service.mapper.ClassroomMapper;
import com.unicore.classroom_service.mapper.StudentListMapper;
import com.unicore.classroom_service.repository.ClassroomRepository;
import com.unicore.classroom_service.repository.StudentListRepository;
import com.unicore.classroom_service.repository.httpclient.ClassEventClient;
import com.unicore.classroom_service.repository.httpclient.ProfileClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentListService {
    private final ClassEventClient classEventClient;
    private final ProfileClient profileClient;

    private final StudentListRepository studentListRepository;
    private final StudentListMapper studentListMapper;

    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;

    // Nhập DS sv nhiều lớp
    public Flux<StudentListResponse> createStudentListBulk(List<StudentListCreationRequest> requests) {
        return Flux.fromIterable(requests)
            .flatMap(this::createStudentList);
    }

    public Mono<StudentListResponse> createInternStudentList(InternStudentListImportRequest request) {
        return classroomRepository.findById(request.getClassId())
            .flatMap(classroom -> {
                for (Subclass subclass : classroom.getSubclasses()) {
                    if (subclass.getType().isMainClass()) {
                        if (subclass.isStudentImported()) return Mono.error(new AppException(ErrorCode.STUDENT_LIST_IMPORTED));
                        else {
                            subclass.setStudentImported(true);
                            return classroomRepository.save(classroom);
                        }
                    }
                }
                return Mono.error(new AppException(ErrorCode.CLASS_NOT_FOUND));
            })
            .map(response -> {
                List<String> studentCodes = request.getStudents()
                    .stream().map(InternStudentRequest::getStudentCode).toList();
                classEventClient.importInternTopics(request).subscribe();
                createSubclassesFromInternList(response, request).subscribe();

                return StudentList.builder()
                    .classId(response.getId())
                    .subclassCode(response.getCode())
                    .semester(response.getSemester())
                    .year(response.getYear())
                    .studentCodes(Set.copyOf(studentCodes))
                    .build();
            })
            .flatMap(studentListRepository::save)
            .map(studentListMapper::toStudentListResponse);
    }

    private Flux<StudentListResponse> createInternStudentListForSubclasses(List<StudentList> lists) {
        return Flux.fromIterable(lists)
            .flatMap(studentListRepository::save)
            .map(studentListMapper::toStudentListResponse);
    }

    private Mono<ClassroomResponse> createSubclassesFromInternList(Classroom classroom, InternStudentListImportRequest request) {
        List<String> emails = request.getStudents().stream().map(InternStudentRequest::getTeacherMail).toList();
        return profileClient.getTeachersByEmails(emails)
            .flatMap(teachers -> {
                Map<String, TeacherResponse> teacherMap = teachers.stream()
                    .collect(Collectors.toMap(
                        TeacherResponse::getEmail,
                        teacher -> teacher 
                    ));

                Map<String, Subclass> subclasses = new HashMap<>();
                Map<String, StudentList> studentLists = new HashMap<>();

                for (InternStudentRequest student : request.getStudents()) {
                    if (!subclasses.containsKey(student.getTeacherMail())) {
                        TeacherResponse teacher = teacherMap.get(student.getTeacherMail());
                        Subclass subclass = Subclass.builder()
                            .code(teacher.getCode())
                            .mainTeacherCode(teacher.getCode())
                            .teacherCodes(List.of(teacher.getCode()))
                            .type(ClassType.NHOM_HUONG_DAN)
                            .build();
                        subclasses.put(student.getTeacherMail(), subclass);

                        StudentList studentList = StudentList.builder()
                            .classId(classroom.getId())
                            .subclassCode(teacher.getCode())
                            .semester(classroom.getSemester())
                            .year(classroom.getYear())
                            .studentCodes(new HashSet<>(Set.of(student.getStudentCode())))
                            .build();
                        studentLists.put(student.getTeacherMail(), studentList);
                    } else {
                        StudentList studentList = studentLists.get(student.getTeacherMail());
                        studentList.getStudentCodes().add(student.getStudentCode());
                    }
                }

                createInternStudentListForSubclasses(List.copyOf(studentLists.values())).subscribe();
                classroom.getSubclasses().addAll(List.copyOf(subclasses.values()));

                return Mono.just(classroom);
            })
            .flatMap(classroomRepository::save)
            .map(classroomMapper::toClassroomResponse);
    }

    public Mono<StudentListResponse> createStudentList(StudentListCreationRequest request) {
        return classroomRepository.findById(request.getClassId())
            .flatMap(classroom -> {
                for (Subclass subclass : classroom.getSubclasses()) {
                    if (subclass.getCode().equals(request.getSubclassCode())) {
                        if (subclass.isStudentImported()) return Mono.error(new AppException(ErrorCode.STUDENT_LIST_IMPORTED));
                        else {
                            subclass.setStudentImported(true);
                            return classroomRepository.save(classroom);
                        }
                    }
                }
                return Mono.error(new AppException(ErrorCode.CLASS_NOT_FOUND));
            })
            .flatMap(temp -> checkDuplicate(request))
            .flatMap(result -> result.equals(Boolean.FALSE) ?
                Mono.just(request)
                    .map(studentListMapper::toStudentList)
                    .flatMap(studentListRepository::save)
                    .map(studentListMapper::toStudentListResponse) :
                Mono.error(() -> new AppException(ErrorCode.STUDENT_LIST_IMPORTED))
            ).switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }
    
    // Thêm sv vào lớp
    public Mono<StudentListResponse> addStudents(AddStudentsToListRequest request) {
        return studentListRepository.findByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(studentList -> {
                studentList.getStudentCodes().addAll(request.getStudentCodes());
                return studentList;
            })
            .flatMap(studentListRepository::save)
            .map(studentListMapper::toStudentListResponse);
    }


    public Mono<StudentListResponse> getStudentList(GetByClassRequest request) {
        if (request.getSubclassCode() == null) {
            return classroomRepository.findById(request.getClassId())
                .flatMap(classroom -> studentListRepository.findByClassIdAndSubclassCode(request.getClassId(), classroom.getCode()))
                .map(studentListMapper::toStudentListResponse)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
        }
        return studentListRepository.findByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(studentListMapper::toStudentListResponse)
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    public Flux<StudentListResponse> getStudentListsByClassId(String classId) {
        return studentListRepository.findByClassId(classId)
            .map(studentListMapper::toStudentListResponse);
    }
    
    // thêm sv ngoài lớplớp
    public Mono<StudentListResponse> addForeignStudents(AddForeignStudentsRequest request) {
        return studentListRepository.findByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(studentList -> {
                studentList.getForeignStudents().addAll(request.getForeignStudents());
                return studentList;
            })
            .flatMap(studentListRepository::save)
            .map(studentListMapper::toStudentListResponse);
    }

    private Mono<Boolean> 
    checkDuplicate(StudentListCreationRequest request) {
        return studentListRepository.findByClassIdAndSubclassCode(
                request.getClassId(),
                request.getSubclassCode()
            )
            .flatMap(result -> validate(request))
            .switchIfEmpty(Mono.just(false));
    }

    private Mono<Boolean> validate(StudentListCreationRequest request) {
        return classroomRepository.findById(request.getClassId())
            .flatMap(classroom -> {
                for (Subclass subclass : classroom.getSubclasses()) {
                    if (subclass.getType().isMainClass() && !subclass.getCode().equals(request.getSubclassCode())) {
                        // lớp con 
                        return studentListRepository.findByClassIdAndSubclassCode(
                            request.getClassId(),
                            request.getSubclassCode()
                        ).map(mainStudentList ->  
                            mainStudentList.getStudentCodes().containsAll(request.getStudentCodes()));
                    } else if (subclass.getCode().equals(request.getSubclassCode())) {
                        return studentListRepository.findByClassIdAndSubclassCode(
                            request.getClassId(),
                            request.getSubclassCode()
                        )
                        .hasElement()
                        .map(hasElement -> !hasElement);
                    }
                }
                return Mono.empty();
            })
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.CLASS_NOT_FOUND)));
    }

    public Mono<List<ClassroomResponse>> getStudentClasses(String studentCode) {
        return studentListRepository.findByStudentCodeInList(studentCode)
            .collectList()
            .flatMap(studentLists -> {
                Map<String, List<String>> classMap = new HashMap<>();
                if (studentLists != null)
                    studentLists.forEach(studentList -> {
                        classMap.putIfAbsent(studentList.getClassId(), new ArrayList<>());
                        classMap.get(studentList.getClassId()).add(studentList.getSubclassCode());
                    });
                return classroomRepository.findAllById(classMap.keySet())
                    .collectList()
                    .map(classrooms -> {
                        for (Classroom classroom : classrooms) {
                            List<Subclass> subclasses = classroom.getSubclasses().stream()
                                .filter(subclass -> classMap.get(classroom.getId()).contains(subclass.getCode()))
                                .toList();
                            classroom.setSubclasses(subclasses);
                        }
                        return classrooms.stream()
                        .map(classroomMapper::toClassroomResponse)
                        .toList();
                    });
            })
            .doOnError(e -> 
                log.error("BUGUGU", e)
            );
    }
}
