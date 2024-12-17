package com.unicore.classevent_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classevent_service.entity.Bookmark;

import reactor.core.publisher.Flux;

public interface BookmarkRepository extends ReactiveMongoRepository<Bookmark, String> {
    Flux<Bookmark> findAllByUserEmail(String userEmail);
}
