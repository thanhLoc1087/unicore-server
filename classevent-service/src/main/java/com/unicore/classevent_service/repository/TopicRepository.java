package com.unicore.classevent_service.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classevent_service.entity.NewTopic;
import com.unicore.classevent_service.entity.ProjectTopic;

import reactor.core.publisher.Flux;

public interface TopicRepository extends ReactiveMongoRepository<NewTopic, String> {
    
    @Query("{ 'type': 'BTL', 'projectId': { $exists: true, $eq: '' } }")
    Flux<ProjectTopic> findAllByProjectId(String projectId);
}
