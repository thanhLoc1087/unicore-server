package com.unicore.profile_service.repository;

import java.util.List;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.profile_service.entity.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentRepository extends ReactiveCrudRepository<Student, String>{
        Flux<Student> findByOrganizationId(String organizationId);   
        Flux<Student> findAllByCode(List<String> codes);   
        Mono<Student> findByCode(String code);
}
