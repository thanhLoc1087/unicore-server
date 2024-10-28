package com.unicore.organization_service.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.organization_service.entity.Subject;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubjectRepository extends ReactiveCrudRepository<Subject, String>{
    Mono<Subject> findByCode(String code);
    Flux<Subject> findByOrganizationId(String orgId);
}
