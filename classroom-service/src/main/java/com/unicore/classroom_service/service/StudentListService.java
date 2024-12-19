package com.unicore.classroom_service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.unicore.classroom_service.dto.request.AddForeignStudentsRequest;
import com.unicore.classroom_service.dto.request.AddStudentsToListRequest;
import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.request.StudentListCreationRequest;
import com.unicore.classroom_service.dto.response.ClassroomResponse;
import com.unicore.classroom_service.dto.response.StudentListResponse;
import com.unicore.classroom_service.entity.Classroom;
import com.unicore.classroom_service.entity.Subclass;
import com.unicore.classroom_service.exception.AppException;
import com.unicore.classroom_service.exception.ErrorCode;
import com.unicore.classroom_service.mapper.ClassroomMapper;
import com.unicore.classroom_service.mapper.StudentListMapper;
import com.unicore.classroom_service.repository.ClassroomRepository;
import com.unicore.classroom_service.repository.StudentListRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentListService {
    private final StudentListRepository studentListRepository;
    private final StudentListMapper studentListMapper;

    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;

    // Nhập DS sv nhiều lớplớp
    public Flux<StudentListResponse> createStudentListBulk(List<StudentListCreationRequest> requests) {
        return Flux.fromIterable(requests)
            .flatMap(this::createStudentList);
    }

    public Mono<StudentListResponse> createStudentList(StudentListCreationRequest request) {
        return checkDuplicate(request)
            .flatMap(result -> result.equals(Boolean.FALSE) ?
                Mono.just(request)
                    .map(studentListMapper::toStudentList)
                    .flatMap(studentListRepository::save)
                    .map(studentListMapper::toStudentListResponse) :
                Mono.error(() -> new AppException(ErrorCode.DUPLICATE))
            );
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

    private Mono<Boolean> checkDuplicate(StudentListCreationRequest request) {
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
                        return studentListRepository.findByClassIdAndSubclassCode(
                            request.getClassId(),
                            request.getSubclassCode()
                        ).map(mainStudentList ->  
                            mainStudentList.getStudentCodes().containsAll(request.getStudentCodes()));
                    }
                }
                return Mono.just(true);
            })
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
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
