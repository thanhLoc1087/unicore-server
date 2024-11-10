package com.unicore.classroom_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classroom_service.entity.StudentGrouping;

import reactor.core.publisher.Mono;


public interface StudentGroupingRepository extends ReactiveCrudRepository<StudentGrouping, String> {
    Mono<StudentGrouping> findByClassIdAndSubclassCode(String classId, String subclassCode);
}
