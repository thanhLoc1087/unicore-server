package com.unicore.profile_service.service;

import org.springframework.dao.DataAccessException;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.unicore.profile_service.exception.AppException;
import com.unicore.profile_service.exception.ErrorCode;
import com.unicore.profile_service.dto.request.TeacherBulkCreationRequest;
import com.unicore.profile_service.dto.request.TeacherCreationRequest;
import com.unicore.profile_service.dto.response.TeacherResponse;
import com.unicore.profile_service.mapper.TeacherMapper;
import com.unicore.profile_service.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public Mono<TeacherResponse> createTeacher(TeacherCreationRequest request) {
        return Mono.just(request)
            .map(teacherMapper::toTeacher)
            .flatMap(teacherRepository::save)
            .map(teacherMapper::toTeacherResponse)
            .onErrorResume(throwable -> {
                if (throwable instanceof BadSqlGrammarException || throwable instanceof DataAccessException) {
                    log.error("R2DBC data invalidation error: {}", throwable.getCause());
                    // Return an error response or rethrow as a custom exception
                    return Mono.error(new AppException(ErrorCode.DUPLICATE));
                }
                return Mono.error(throwable); // Propagate other exceptions
            })
            .doOnSuccess(response -> {
                // goi kafka tao Account
            });
    }

    @Transactional
    public Flux<TeacherResponse> createTeachers(TeacherBulkCreationRequest request) {
        return Flux.fromIterable(request.getTeachers())
            .map(teacher -> {
                teacher.setOrganizationId(request.getOrganizationId());
                return teacher;
            })
            .flatMap(this::createTeacher);
    }

    public Mono<TeacherResponse> getTeacherById(String id) {
        return teacherRepository.findById(id)
            .map(teacherMapper::toTeacherResponse);
    }

    public Mono<TeacherResponse> getTeacherByCode(String code) {
        return teacherRepository.findByCode(code)
            .map(teacherMapper::toTeacherResponse);
    }

    public Flux<TeacherResponse> getTeachers() {
        return teacherRepository.findAll()
            .map(teacherMapper::toTeacherResponse);
    }

    public Flux<TeacherResponse> getTeachersByOrganizationId(String organizationId) {
        return teacherRepository.findByOrganizationId(organizationId)
            .map(teacherMapper::toTeacherResponse);
    }

    public Mono<Void> deleteById(String id) {
        return teacherRepository.deleteById(id);
    }

    public Mono<Void> deleteByIds(List<String> ids) {
        return teacherRepository.deleteAllById(ids);
    }
}
