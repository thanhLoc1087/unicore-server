package com.unicore.classevent_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classevent_service.entity.Group;

import reactor.core.publisher.Flux;

public interface GroupRepository extends ReactiveMongoRepository<Group, String> {
    Flux<Group> findByGroupingId(String groupingId);
}
