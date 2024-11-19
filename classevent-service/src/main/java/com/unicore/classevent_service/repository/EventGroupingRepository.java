package com.unicore.classevent_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classevent_service.entity.EventGrouping;

import reactor.core.publisher.Mono;

public interface EventGroupingRepository extends ReactiveCrudRepository<EventGrouping, String> {
    Mono<EventGrouping> findByEventId(String eventId);
}
