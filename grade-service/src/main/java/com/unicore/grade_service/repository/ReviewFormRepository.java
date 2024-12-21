package com.unicore.grade_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.grade_service.entity.ReviewForm;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewFormRepository extends ReactiveMongoRepository<ReviewForm, String>{
    Flux<ReviewForm> findAllByProjectId(String projectId);
    Mono<ReviewForm> findByProjectIdAndTopicId(String projectId, String topicId);
}
