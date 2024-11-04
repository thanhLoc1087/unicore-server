package com.unicore.classroom_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classroom_service.entity.Classroom;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClassroomRepository extends ReactiveMongoRepository<Classroom, String> {    
    public Mono<Classroom> findByCode(String code);
    public Flux<Classroom> findByOrganizationId(String organizationId);
    public Mono<Classroom> findByCodeAndSemesterAndYear(String code, int semester, int year);
}
