package com.unicore.profile_service.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unicore.profile_service.dto.request.TeacherUpdateRequest;
import com.unicore.profile_service.dto.request.GetByClass;
import com.unicore.profile_service.dto.request.TeacherBulkCreationRequest;
import com.unicore.profile_service.dto.request.TeacherCreationRequest;
import com.unicore.profile_service.dto.response.Subclass;
import com.unicore.profile_service.dto.response.TeacherResponse;
import com.unicore.profile_service.exception.AppException;
import com.unicore.profile_service.exception.ErrorCode;
import com.unicore.profile_service.mapper.TeacherMapper;
import com.unicore.profile_service.repository.TeacherRepository;
import com.unicore.profile_service.repository.httpclient.ClassroomClient;

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

    private final ClassroomClient classroomClient;

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
            })
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
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
            .map(teacherMapper::toTeacherResponse)
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    public Flux<TeacherResponse> getTeachersByClass(GetByClass request) {
        return classroomClient.getClassById(request.getClassId()) 
            .map(classroom -> {
                Set<String> teacherCodes = new HashSet<>();
                if (classroom.getType().isMainClass() && classroom.isOrgManaged()) {
                    for (Subclass subclass : classroom.getSubclasses()) {
                        if (!subclass.getType().isMainClass()) {
                            teacherCodes.addAll(subclass.getTeacherCodes());
                        }
                    }
                } else {
                    for (Subclass subclass : classroom.getSubclasses()) {
                        teacherCodes.addAll(subclass.getTeacherCodes());
                    }
                }
                return List.copyOf(teacherCodes);
            })
            .flatMapMany(teacherRepository::findAllByCodeIn)
            .map(teacherMapper::toTeacherResponse);
    }

    public Mono<TeacherResponse> getTeacherByCode(String code) {
        return teacherRepository.findByCode(code)
            .map(teacherMapper::toTeacherResponse);
    }
    public Flux<TeacherResponse> getTeacherByCodes(Set<String> codes) {
        return Flux.fromIterable(codes)
            .flatMap(this::getTeacherByCode);
    }

    public Mono<TeacherResponse> getTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email)
            .map(teacherMapper::toTeacherResponse);
    }

    public Flux<TeacherResponse> getTeacherByEmails(Set<String> emails) {
        return Flux.fromIterable(emails)
            .flatMap(this::getTeacherByEmail);
    }

    public Flux<TeacherResponse> getTeachers() {
        return teacherRepository.findAll()
            .map(teacherMapper::toTeacherResponse);
    }

    public Flux<TeacherResponse> getTeachersByOrganizationId(String organizationId) {
        return teacherRepository.findByOrganizationId(organizationId)
            .map(teacherMapper::toTeacherResponse);
    }

    public Mono<TeacherResponse> updateTeacher(TeacherUpdateRequest request) {
        return teacherRepository.findById(request.getId())
            .map(teacher -> {
                teacherMapper.updateTeacher(teacher, request);
                return teacher;
            })
            .flatMap(teacherRepository::save)
            .map(teacherMapper::toTeacherResponse);
    }

    public Flux<TeacherResponse> updateTeacherBulks(List<TeacherUpdateRequest> requests) {
        return Flux.fromIterable(requests)
            .flatMap(this::updateTeacher);
    }
    
    public Mono<TeacherResponse> deleteById(String id) {
        return teacherRepository.findById(id)
            .flatMap(student -> {
                student.setDeleted(true);
                return teacherRepository.save(student);
            })
            .map(teacherMapper::toTeacherResponse);
    }

    public Flux<TeacherResponse> deleteByIds(List<String> ids) {
        return Flux.fromIterable(ids)
            .flatMap(this::deleteById);
    }
}
