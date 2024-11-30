package com.unicore.classevent_service.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.ReviewCreationRequest;
import com.unicore.classevent_service.dto.request.ReviewFeedbackRequest;
import com.unicore.classevent_service.dto.response.BaseEventResponse;
import com.unicore.classevent_service.dto.response.SubmissionReviewResponse;
import com.unicore.classevent_service.entity.SubmissionReview;
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

    public Mono<SubmissionReviewResponse> createSubmissionReview(ReviewCreationRequest request) {
        SubmissionReview review = SubmissionReview.builder()
            .submissionId(request.getSubmissionId())
            .submitterId(request.getSubmitterId())
            .nameId(request.getNameId())
            .classId(request.getClassId())
            .subclassCode(request.getSubclassCode())
            .createdDate(Date.from(Instant.now()))
            .createdBy("LOC SV")
            .build();
        return repository.save(review)
            .map(mapper::toResponse);
    }

    public Mono<SubmissionReviewResponse> feedbackSubmissionReview(ReviewFeedbackRequest request) {
        SubmissionReview review = SubmissionReview.builder()
            .grade(request.getGrade())
            .feedback(request.getFeedback())
            .feedbackDate(Date.from(Instant.now()))
            .reviewerId("LOC")
            .build();
        return repository.save(review)
            .map(mapper::toResponse);
    }

    public Flux<SubmissionReviewResponse> getSubmissionReviewByClass(GetByClassRequest request) {
        return repository.findAllByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(mapper::toResponse);
    }
}
