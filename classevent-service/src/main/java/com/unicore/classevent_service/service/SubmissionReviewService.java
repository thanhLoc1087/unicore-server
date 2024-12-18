package com.unicore.classevent_service.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.ReviewCreationRequest;
import com.unicore.classevent_service.dto.request.ReviewFeedbackRequest;
import com.unicore.classevent_service.dto.response.SubmissionReviewResponse;
import com.unicore.classevent_service.entity.SubmissionReview;
import com.unicore.classevent_service.enums.ReviewStatus;
import com.unicore.classevent_service.mapper.SubmissionReviewMapper;
import com.unicore.classevent_service.repository.SubmissionReviewRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SubmissionReviewService {
    private final SubmissionReviewRepository repository;
    private final SubmissionReviewMapper mapper;

    // Xin phúc khảo
    public Mono<SubmissionReviewResponse> createSubmissionReview(ReviewCreationRequest request) {
        SubmissionReview review = SubmissionReview.builder()
            .submissionId(request.getSubmissionId())
            .submitterId(request.getSubmitterId())
            .submitterName(request.getSubmitterName())
            .reviewerId(request.getReviewerId())
            .classId(request.getClassId())
            .subclassCode(request.getSubclassCode())
            .createdDate(Date.from(Instant.now()))
            .createdBy("LOC SV")
            .build();
        return repository.save(review)
            .map(mapper::toResponse);
    }

    // Chấm điểm 
    public Mono<SubmissionReviewResponse> feedbackSubmissionReview(String reviewId, ReviewFeedbackRequest request) {
        return repository.findById(reviewId)
            .map(response -> {
                response.getGrades().add(request.getGrade());
                response.getFeedbacks().add(request.getFeedback());
                response.getFeedbackDates().add(Date.from(Instant.now()));
                response.setStatus(ReviewStatus.REVIEWED);
                return response;
            })
            .flatMap(repository::save)
            .map(mapper::toResponse);
    }

    // Tuừ chối
    public Mono<SubmissionReviewResponse> turnDownSubmissionReview(String reviewId) {
        return repository.findById(reviewId)
            .map(response -> {
                response.setStatus(ReviewStatus.TURNED_DOWN);
                response.setModifiedDate(Date.from(Instant.now()));
                return response;
            })
            .flatMap(repository::save)
            .map(mapper::toResponse);
    }

    public Flux<SubmissionReviewResponse> getSubmissionReviewByClass(GetByClassRequest request) {
        return repository.findAllByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(mapper::toResponse);
    }

    public Flux<SubmissionReviewResponse> getSubmissionReviewByReviewer(String reviewerId) {
        return repository.findAllByReviewerId(reviewerId)
            .map(mapper::toResponse);
    }

    public Flux<SubmissionReviewResponse> getSubmissionReviewByReviewerAndStatus(String reviewerId, ReviewStatus status) {
        return repository.findAllByReviewerIdAndStatus(reviewerId, status)
            .map(mapper::toResponse);
    }
}
