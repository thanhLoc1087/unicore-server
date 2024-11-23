package com.unicore.classevent_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classevent_service.entity.Submission;

import reactor.core.publisher.Flux;

public interface SubmissionRepository extends ReactiveCrudRepository<Submission, String>{
    public Flux<Submission> findAllByEventId(String eventId);
}
