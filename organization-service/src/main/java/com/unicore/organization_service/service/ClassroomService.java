package com.unicore.organization_service.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unicore.organization_service.dto.request.ClassBulkCreationRequest;
import com.unicore.organization_service.dto.request.ClassCreationRequest;
import com.unicore.organization_service.dto.response.ClassroomResponse;
import com.unicore.organization_service.exception.AppException;
import com.unicore.organization_service.exception.ErrorCode;
import com.unicore.organization_service.mapper.ClassroomMapper;
import com.unicore.organization_service.repository.ClassroomRepository;

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
    private final SubjectService subjectService;

    public Mono<ClassroomResponse> createClassroom(ClassCreationRequest request) {
        return Mono.just(request)
            .flatMap(newClass -> checkDuplicate(newClass.getCode(), newClass.getSemester(), newClass.getYear()))
            .flatMap(result -> Boolean.TRUE.equals(result) ? 
                Mono.error(new AppException(ErrorCode.DUPLICATE)) :
                saveClassroom(request)
            );
    }

    private Mono<ClassroomResponse> saveClassroom(ClassCreationRequest request) {
        return Mono.just(request)
                .map(classroomMapper::toClassroom)
                .flatMap(classroomRepository::save)
                .map(classroomMapper::toClassroomResponse)
                .onErrorResume(throwable -> {
                    if (throwable instanceof BadSqlGrammarException || throwable instanceof DataAccessException) {
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
    public Flux<ClassroomResponse> createClassrooms(ClassBulkCreationRequest request) {
        return Flux.fromIterable(request.getClasses())
            .map(classroom -> {
                classroom.setOrganizationId(request.getOrganizationId());
                return classroom;
            })
            .flatMap(this::createClassroom);
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
}
