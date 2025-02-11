package com.unicore.organization_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;

import com.unicore.organization_service.exception.AppException;
import com.unicore.organization_service.exception.ErrorCode;
import com.unicore.organization_service.dto.request.GetSubjectByYearAndSemesterRequest;
import com.unicore.organization_service.dto.request.SubjectBulkCreationRequest;
import com.unicore.organization_service.dto.request.SubjectCreationRequest;
import com.unicore.organization_service.dto.response.SubjectInListResponse;
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
        return checkDuplicateSubject(request.getCode(), request.getOrganizationId())
            .flatMap(subjectId -> subjectId.isEmpty() 
                ? saveNewSubject(request)
                : updateExistingSubject(subjectId, request)
            )
            .onErrorResume(e -> Mono.error(new RuntimeException("Failed to create subject", e)))
            .as(transactionalOperator::transactional);
    }

    @Transactional
    public Mono<List<SubjectInListResponse>> createSubjects(SubjectBulkCreationRequest request) {
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
            .map(SubjectInListResponse::fromSubject)
            .collectList()
            .doOnError(throwable -> log.error("Error creating subjects: {}", throwable.getMessage(), throwable));
    }

    private Mono<SubjectResponse> saveNewSubject(SubjectCreationRequest request) {
        log.info("save new subject: " + request.toString());
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
        log.info("update subject: " + request.toString());
        SubjectMetadata metadata = subjectMapper.toSubjectMetadata(request);
        return subjectRepository.findById(subjectId)
            .map(response -> {
                response = subjectMapper.toSubject(request);
                return response;
            })
            .flatMap(subjectRepository::save)
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

    private Mono<SubjectMetadata> createSubjectMetadata(SubjectMetadata subjectMetadata) {
        return checkDuplicateMetadata(subjectMetadata.getSubjectId(), subjectMetadata.getSemester(), subjectMetadata.getYear())
            .flatMap(isDuplicate -> {
                log.info("Check duplicate metadata: " + isDuplicate);
                return Boolean.TRUE.equals(isDuplicate) 
                    ? Mono.error(new AppException(ErrorCode.DUPLICATE)) 
                    : subjectMetadataRepository.save(subjectMetadata);
            })
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    private Mono<String> checkDuplicateSubject(String subjectCode, String orgId) {
        return subjectRepository.findByCodeAndOrganizationId(subjectCode, orgId)
            .map(subject -> {
                log.info("Duplicated Subject Id: " + subject.getId());
                return subject.getId();
            })
            .onErrorResume(e -> Mono.error(new RuntimeException("Failed to check duplicate subject", e)))
            .defaultIfEmpty("");
    }

    private Mono<Boolean> checkDuplicateMetadata(String subjectId, int semester, int year) {
        return subjectMetadataRepository.findBySubjectIdAndSemesterAndYear(subjectId, semester, year)
            .hasElements();
    }
    
    public Flux<SubjectInListResponse> getSubjects() {
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
            .map(SubjectInListResponse::fromSubject)
            .switchIfEmpty(Mono.error(new Exception("Subject list empty")));
    }
    
    public Flux<SubjectInListResponse> getSubjectsByOrg(String orgId) {
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
            .map(SubjectInListResponse::fromSubject)
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

    public Flux<SubjectResponse> getSubjectsBySemesterAndYear(GetSubjectByYearAndSemesterRequest request) {
        return subjectRepository.findAll()
            .map(subjectMapper::toSubjectResponse)
            .flatMap(subject -> subjectMetadataRepository.findBySubjectId(subject.getId())
                .collectList()
                .map(subjectMets -> {
                    SubjectMetadata result = subjectMets.get(0);
                    for (SubjectMetadata subjectMet : subjectMets) {
                        if (subjectMet.isGreaterThan(request.getSemester(), request.getYear()) 
                            && !subjectMet.isGreaterThan(result.getSemester(), request.getYear())) {
                                result = subjectMet;
                            }
                        
                    }
                    subject.setMetadata(result);
                    return subject;
                })
            );
    }

    public Mono<SubjectResponse> deleteById(String id) {
        return subjectMetadataRepository.findBySubjectId(id)
            .flatMap(metadata -> {
                metadata.setDeleted(true);   
                return subjectMetadataRepository.save(metadata);
            })
            .then(
                subjectRepository.findById(id)
                    .flatMap(subject -> {
                        subject.setDeleted(true);
                        return subjectRepository.save(subject);
                    })
                    .map(subjectMapper::toSubjectResponse)
            );
    }

    public Flux<SubjectResponse> deleteByIds(List<String> ids) {
        return Flux.fromIterable(ids)
            .flatMap(this::deleteById);
    }
}
