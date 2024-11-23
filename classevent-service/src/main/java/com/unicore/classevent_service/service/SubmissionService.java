package com.unicore.classevent_service.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.SubmissionCreationRequest;
import com.unicore.classevent_service.dto.request.SubmissionFeedbackRequest;
import com.unicore.classevent_service.dto.response.BaseEventResponse;
import com.unicore.classevent_service.dto.response.SubmissionResponse;
import com.unicore.classevent_service.entity.Submission;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.mapper.SubmissionMapper;
import com.unicore.classevent_service.repository.SubmissionRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository repository;
    private final SubmissionMapper mapper;
    private final HomeworkService homeworkService;
    private final ProjectService projectService;
    private final ReportService reportService;

    public Mono<SubmissionResponse> createSubmission(SubmissionCreationRequest request) {
        Submission submission = mapper.toSubmission(request);
        return Mono.just(request)
            .flatMap(creationRequest -> {
                if (creationRequest.getEventType() == EventType.HOMEWORK)
                    return homeworkService.getHomework(creationRequest.getEventId());
                if (creationRequest.getEventType() == EventType.PROJECT)
                    return projectService.getProject(creationRequest.getEventId());
                else 
                    return reportService.getReport(creationRequest.getEventId());
            })
            .map((BaseEventResponse event) -> {
                submission.setGroup(event.isInGroup());
                submission.setCreatedDate(Date.from(Instant.now()));
                return submission;
            })
            .flatMap(repository::save)
            .map(mapper::toSubmissionResponse);
    }

    public Mono<SubmissionResponse> addFeedback(String id, SubmissionFeedbackRequest request) {
        return repository.findById(id)
            .map(submission -> {
                submission.setFeedback(request.getFeedback());
                submission.setGrade(request.getGrade());
                submission.setFeedbackDate(Date.from(Instant.now()));
                submission.setReviewerId("Loc");
                return submission;
            })
            .flatMap(repository::save)
            .map(mapper::toSubmissionResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<SubmissionResponse> getSubmissionsByEventId(String eventId) {
        return repository.findAllByEventId(eventId)
            .map(mapper::toSubmissionResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<SubmissionResponse> getSubmissionById(String id) {
        return repository.findById(id)
            .map(mapper::toSubmissionResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
}
