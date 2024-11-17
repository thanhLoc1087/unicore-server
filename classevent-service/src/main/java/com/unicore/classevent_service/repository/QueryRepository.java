package com.unicore.classevent_service.repository;

import java.time.LocalDateTime;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classevent_service.entity.Query;

import reactor.core.publisher.Flux;

public interface QueryRepository extends ReactiveCrudRepository<Query, String> {
    Flux<Query> findByClassIdAndSubclassCode(String classId, String subclassCode);


    @org.springframework.data.mongodb.repository.Query(
        "{ 'class_id' : { $lte : ?0 }, 'subclass_code' : { $lte : ?1 }, 'publishDate' : { $lte : ?2 }, 'endDate' : { $gte : ?3 } }")
    Flux<Query> findActiveQueries(String classId, String subclassCode, LocalDateTime todayStartDate, LocalDateTime todayEndDate);
}
