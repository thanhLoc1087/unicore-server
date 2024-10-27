package com.unicore.organization_service.service;

import org.springframework.stereotype.Service;

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

    public Mono<SubjectResponse> createSubject(SubjectCreationRequest request) {
        SubjectMetadata metadata = subjectMapper.toSubjectMetadata(request);
        Subject subject = subjectMapper.toSubject(request);

        return subjectRepository.save(subject)
            .map((savedSubject -> {
                metadata.setSubjectId(savedSubject.getId());
                return subjectMapper.toSubjectResponse(savedSubject);
            }))
            .flatMap(savedSubject ->  subjectMetadataRepository.save(metadata)
                    .map(savedMetadata -> {
                        savedSubject.setMetadata(savedMetadata);
                        return savedSubject;
                    })
            )
            .doOnError(throwable -> log.error("Error creating subject: {}", throwable.getMessage(), throwable));
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
            );
    }


    public Mono<Boolean> checkDuplicateMetadata(String subjectId, int semester, int year) {
        return subjectMetadataRepository.findBySubjectIdAndSemesterAndYear(subjectId, semester, year)
            .flatMap(metadata -> Mono.just(true))
            .switchIfEmpty(Mono.just(false));
    }

    public Mono<Void> deleteById(String id) {
        return subjectRepository.deleteById(id);
    }
}
