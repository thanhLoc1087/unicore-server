package com.unicore.classevent_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classevent_service.entity.GeneralTest;

import reactor.core.publisher.Flux;

public interface GeneralTestRepository extends ReactiveCrudRepository<GeneralTest, String> {
    Flux<GeneralTest> findAllByClassIdAndSubclassCode(String classId, String subclassCode);
}
