package com.unicore.classroom_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unicore.classroom_service.dto.request.StudentListCreationRequest;
import com.unicore.classroom_service.dto.response.StudentListResponse;
import com.unicore.classroom_service.entity.Subclass;
import com.unicore.classroom_service.exception.AppException;
import com.unicore.classroom_service.exception.ErrorCode;
import com.unicore.classroom_service.mapper.StudentListMapper;
import com.unicore.classroom_service.repository.ClassroomRepository;
import com.unicore.classroom_service.repository.StudentListRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StudentListService {
    private final StudentListRepository studentListRepository;
    private final StudentListMapper studentListMapper;

    private final ClassroomRepository classroomRepository;

    public Flux<StudentListResponse> createStudentListBulk(List<StudentListCreationRequest> requests) {
        return Flux.fromIterable(requests)
            .flatMap(this::createStudentList);
    }

    public Mono<StudentListResponse> createStudentList(StudentListCreationRequest request) {
        return checkDuplicate(request)
            .flatMap(result -> result.equals(Boolean.TRUE) ?
                Mono.just(request)
                    .map(studentListMapper::toStudentList)
                    .flatMap(studentListRepository::save)
                    .map(studentListMapper::toStudentListResponse) :
                Mono.error(() -> new AppException(ErrorCode.DUPLICATE))
            );
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
}
