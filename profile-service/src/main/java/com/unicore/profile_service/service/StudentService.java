package com.unicore.profile_service.service;

import org.springframework.dao.DataAccessException;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.unicore.profile_service.exception.AppException;
import com.unicore.profile_service.exception.ErrorCode;
import com.unicore.profile_service.dto.request.GetClassMemberRequest;
import com.unicore.profile_service.dto.request.StudentBulkCreationRequest;
import com.unicore.profile_service.dto.request.StudentCreationRequest;
import com.unicore.profile_service.dto.response.StudentResponse;
import com.unicore.profile_service.mapper.StudentMapper;
import com.unicore.profile_service.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public Mono<StudentResponse> createStudent(StudentCreationRequest request) {
        return Mono.just(request)
            .map(studentMapper::toStudent)
            .flatMap(studentRepository::save)
            .map(studentMapper::toStudentResponse)
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
    public Flux<StudentResponse> createStudents(StudentBulkCreationRequest request) {
        return Flux.fromIterable(request.getStudents())
            .map(student -> {
                student.setOrganizationId(request.getOrganizationId());
                return student;
            })
            .flatMap(this::createStudent);
    }

    public Mono<StudentResponse> getStudentById(String id) {
        return studentRepository.findById(id)
            .map(studentMapper::toStudentResponse)
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    public Mono<StudentResponse> getStudentByCode(String code) {
        return studentRepository.findByCode(code)
            .map(studentMapper::toStudentResponse)
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    public Flux<StudentResponse> getStudents() {
        return studentRepository.findAll()
            .map(studentMapper::toStudentResponse);
    }
    
    public Flux<StudentResponse> getStudentsByCodes(GetClassMemberRequest request) {
        String leaderCode = request.getLeaderCode();
    
        return studentRepository.findAllByCodeIn(request.getStudentCodes())
            .map(studentMapper::toStudentResponse)
            .sort((student1, student2) -> {
                if (student1.getCode().equals(leaderCode)) return -1;
                if (student2.getCode().equals(leaderCode)) return 1;
                return 0;
            });
    }

    public Flux<StudentResponse> getStudentsByOrganizationId(String organizationId) {
        return studentRepository.findByOrganizationId(organizationId)
            .map(studentMapper::toStudentResponse);
    }

    public Mono<Void> deleteById(String id) {
        return studentRepository.deleteById(id);
    }

    public Mono<Void> deleteByIds(List<String> ids) {
        return studentRepository.deleteAllById(ids);
    }
}
