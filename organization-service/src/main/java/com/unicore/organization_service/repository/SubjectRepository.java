package com.unicore.organization_service.repository;


import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.organization_service.entity.Subject;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface SubjectRepository extends ReactiveCrudRepository<Subject, String>{
    Mono<Subject> findByCode(String code);
    
    @Query("SELECT * FROM subject WHERE deleted = 0")
    Flux<Subject> findAllAlive();
    Flux<Subject> findByOrganizationId(String orgId);
    Mono<Subject> findByCodeAndOrganizationId(String code, String organizationId);
}
