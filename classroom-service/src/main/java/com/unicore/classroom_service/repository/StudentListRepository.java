package com.unicore.classroom_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classroom_service.entity.StudentList;

import reactor.core.publisher.Mono;


public interface StudentListRepository extends ReactiveCrudRepository<StudentList, String> {
    Mono<StudentList> findByClassIdAndSubclassCode(String classId, String subclassCode);
}
