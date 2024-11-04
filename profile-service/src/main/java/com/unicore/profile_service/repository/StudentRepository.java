package com.unicore.profile_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.profile_service.entity.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentRepository extends ReactiveCrudRepository<Student, String>{
        Flux<Student> findByOrganizationId(String organizationId);   
        Mono<Student> findByCode(String code);
}