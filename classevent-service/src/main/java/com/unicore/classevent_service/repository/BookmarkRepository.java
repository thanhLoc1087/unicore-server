package com.unicore.classevent_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classevent_service.entity.Bookmark;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.unicore.classevent_service.enums.EventType;

public interface BookmarkRepository extends ReactiveMongoRepository<Bookmark, String> {
    Flux<Bookmark> findAllByUserEmail(String userEmail);
    Flux<Bookmark> findAllByUserEmailAndEventType(String userEmail, EventType eventType);
    Mono<Void> deleteByUserEmailAndEventId(String userEmail, String eventId);
}
