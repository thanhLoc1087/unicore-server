package com.unicore.profile_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.profile_service.entity.Teacher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TeacherRepository extends ReactiveCrudRepository<Teacher, String>{
    Flux<Teacher> findByOrganizationId(String organizationId);   
    Mono<Teacher> findByCode(String code);
    Mono<Teacher> findByEmail(String email);
}
