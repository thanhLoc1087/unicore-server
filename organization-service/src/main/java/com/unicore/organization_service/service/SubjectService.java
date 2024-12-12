package com.unicore.organization_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;

import com.unicore.organization_service.exception.AppException;
import com.unicore.organization_service.exception.ErrorCode;
import com.unicore.organization_service.dto.request.SubjectBulkCreationRequest;
import com.unicore.organization_service.dto.request.SubjectCreationRequest;
import com.unicore.organization_service.dto.response.SubjectResponse;
import com.unicore.organization_service.entity.Subject;
import com.unicore.organization_service.entity.SubjectMetadata;
import com.unicore.organization_service.mapper.SubjectMapper;
import com.unicore.organization_service.repository.SubjectMetadataRepository;
import com.unicore.organization_service.repository.SubjectRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectService {
    SubjectRepository subjectRepository;
    SubjectMetadataRepository subjectMetadataRepository;
    SubjectMapper subjectMapper;
    TransactionalOperator transactionalOperator;

    @Transactional
    public Mono<SubjectResponse> createSubject(SubjectCreationRequest request) {
        return checkDuplicateSubject(request.getCode())
            .flatMap(subjectId -> subjectId.isEmpty() 
                ? saveNewSubject(request)
                : updateExistingSubject(subjectId, request)
            )
            .onErrorResume(e -> Mono.error(new RuntimeException("Failed to create subject", e)))
            .doOnError(throwable -> log.error("Error creating subject: " + throwable.getMessage(), throwable))
            .as(transactionalOperator::transactional);
    }

    @Transactional
    public Mono<List<SubjectResponse>> createSubjects(SubjectBulkCreationRequest request) {
        return Flux.fromIterable(request.getSubjects())
            .map(subject -> {
                subject.setOrganizationId(request.getOrganizationId());
                return subject;
            })
            .flatMap(subject -> createSubject(subject)
                .doOnNext(response -> log.info("Created subject: {}", response))
                .onErrorResume(e -> {
                    log.error("Failed to create subject for request: {} due to error: {}", request, e.getMessage());
                    return Mono.empty();
                })
            )
            .collectList()
            .doOnError(throwable -> log.error("Error creating subjects: {}", throwable.getMessage(), throwable));
    }

    private Mono<SubjectResponse> saveNewSubject(SubjectCreationRequest request) {
        Subject subject = subjectMapper.toSubject(request);
        SubjectMetadata metadata = subjectMapper.toSubjectMetadata(request);
        return subjectRepository.save(subject)
            .map(subjectMapper::toSubjectResponse)
            .flatMap(savedSubject -> {
                metadata.setSubjectId(savedSubject.getId());
                return createSubjectMetadata(metadata)
                    .map(savedMetadata -> {
                        savedSubject.setMetadata(savedMetadata);
                        return savedSubject;
                    });
            });
    }

    private Mono<SubjectResponse> updateExistingSubject(String subjectId, SubjectCreationRequest request) {
        Subject subject = subjectMapper.toSubject(request);
        subject.setId(subjectId);
        SubjectMetadata metadata = subjectMapper.toSubjectMetadata(request);
        metadata.setSubjectId(subjectId);
        return createSubjectMetadata(metadata)
            .map(savedMetadata -> {
                SubjectResponse response = subjectMapper.toSubjectResponse(subject);
                response.setMetadata(savedMetadata);
                return response;
            });
    }

    private Mono<SubjectMetadata> createSubjectMetadata(SubjectMetadata subjectMetadata) {
        return checkDuplicateMetadata(subjectMetadata.getSubjectId(), subjectMetadata.getSemester(), subjectMetadata.getYear())
            .flatMap(isDuplicate -> Boolean.TRUE.equals(isDuplicate) 
                ? Mono.error(new AppException(ErrorCode.DUPLICATE)) 
                : subjectMetadataRepository.save(subjectMetadata)
            )
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    private Mono<String> checkDuplicateSubject(String subjectCode) {
        return subjectRepository.findByCode(subjectCode)
            .map(subject -> {
                log.info("HELOOOO" + subject.getId());
                return subject.getId();
            })
            .onErrorResume(e -> Mono.error(new RuntimeException("Failed to check duplicate subject", e)))
            .defaultIfEmpty("");
    }

    private Mono<Boolean> checkDuplicateMetadata(String subjectId, int semester, int year) {
        return subjectMetadataRepository.findBySubjectIdAndSemesterAndYear(subjectId, semester, year)
            .hasElement();
    }
    
    public Flux<SubjectResponse> getSubjects() {
        return subjectRepository.findAll()
            .map(subjectMapper::toSubjectResponse)
            .flatMap(subject ->  
                subjectMetadataRepository.findTopBySubjectOrderByYearDescAndSemesterDesc(subject.getId())
                    .map(metadata -> {
                        subject.setMetadata(metadata);
                        return subject;
                    })
                    .switchIfEmpty(Mono.just(subject))
            )
            .switchIfEmpty(Mono.error(new Exception("Subject list empty")));
    }
    
    public Flux<SubjectResponse> getSubjectsByOrg(String orgId) {
        return subjectRepository.findByOrganizationId(orgId)
            .map(subjectMapper::toSubjectResponse)
            .flatMap(subject ->  
                subjectMetadataRepository.findTopBySubjectOrderByYearDescAndSemesterDesc(subject.getId())
                    .map(metadata -> {
                        subject.setMetadata(metadata);
                        return subject;
                    })
                    .switchIfEmpty(Mono.just(subject))
            )
            .switchIfEmpty(Mono.error(new Exception("Subject list empty")));
    }

    public Mono<SubjectResponse> getSubjectById(String id) {
        return subjectRepository.findById(id)
            .map(subjectMapper::toSubjectResponse)
            .flatMap(subject ->  
                subjectMetadataRepository.findTopBySubjectOrderByYearDescAndSemesterDesc(subject.getId())
                    .map(metadata -> {
                        subject.setMetadata(metadata);
                        return subject;
                    })
                    .switchIfEmpty(Mono.just(subject))
            )
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    public Mono<SubjectResponse> getSubjectByCode(String code) {
        return subjectRepository.findByCode(code)
            .map(subjectMapper::toSubjectResponse)
            .flatMap(subject ->  
                subjectMetadataRepository.findTopBySubjectOrderByYearDescAndSemesterDesc(subject.getId())
                    .map(metadata -> {
                        subject.setMetadata(metadata);
                        return subject;
                    })
                    .switchIfEmpty(Mono.just(subject))
            )
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    public Mono<Void> deleteById(String id) {
        return subjectRepository.deleteById(id);
    }
}
