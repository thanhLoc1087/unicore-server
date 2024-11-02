package com.unicore.organization_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.organization_service.entity.Classroom;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClassroomRepository extends ReactiveCrudRepository<Classroom, String>{
        Flux<Classroom> findByOrganizationId(String organizationId);   
        Mono<Classroom> findByCode(String code);
        Mono<Classroom> findByCodeAndSemesterAndYear(String code, int semester, int year);
}
