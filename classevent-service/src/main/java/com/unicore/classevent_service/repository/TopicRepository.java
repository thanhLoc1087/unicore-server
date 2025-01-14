package com.unicore.classevent_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classevent_service.entity.NewTopic;

public interface TopicRepository extends ReactiveMongoRepository<NewTopic, String> {
    
}
