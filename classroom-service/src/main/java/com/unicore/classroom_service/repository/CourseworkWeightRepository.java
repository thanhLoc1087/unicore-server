package com.unicore.classroom_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classroom_service.entity.CourseworkWeight;

import reactor.core.publisher.Mono;

public interface CourseworkWeightRepository extends ReactiveCrudRepository<CourseworkWeight, String> {
    public Mono<CourseworkWeight> findByClassIdAndSubclassCode(String classId, String subclassCode);
}
