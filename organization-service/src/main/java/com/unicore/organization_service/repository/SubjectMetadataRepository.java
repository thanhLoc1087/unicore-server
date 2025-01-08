package com.unicore.organization_service.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.organization_service.entity.SubjectMetadata;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface SubjectMetadataRepository extends ReactiveCrudRepository<SubjectMetadata, String> {
    
    @Query("SELECT * FROM subject_metadata WHERE subject_id = :subjectId "
        + "AND deleted = 0 ORDER BY year DESC, semester DESC LIMIT 1")
    Mono<SubjectMetadata> findTopBySubjectOrderByYearDescAndSemesterDesc(@Param("subjectId") String subjectId);

    Mono<SubjectMetadata> findBySubjectIdAndSemesterAndYear(Object subjectId, int semester, int year);

    Mono<Void> deleteAllBySubjectId(String subjectId);

    Flux<SubjectMetadata> findBySubjectId(String subjectId);
}
