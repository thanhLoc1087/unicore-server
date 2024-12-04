package com.unicore.classevent_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classevent_service.entity.SubmissionReview;

import reactor.core.publisher.Flux;


public interface SubmissionReviewRepository extends ReactiveCrudRepository<SubmissionReview, String> {
    Flux<SubmissionReview> findAllBySubmissionId(String submissionId);
    Flux<SubmissionReview> findAllByEventId(String eventId);
    Flux<SubmissionReview> findAllByClassIdAndSubclassCode(String classId, String subclassCode);
    Flux<SubmissionReview> findAllByReviewerId(String reviewerId);
}
