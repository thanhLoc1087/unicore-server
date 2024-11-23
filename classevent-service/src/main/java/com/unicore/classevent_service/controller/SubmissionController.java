package com.unicore.classevent_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.unicore.classevent_service.dto.request.SubmissionCreationRequest;
import com.unicore.classevent_service.dto.request.SubmissionFeedbackRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.SubmissionResponse;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.service.SubmissionService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;

    @GetMapping("/{id}")
    public Mono<ApiResponse<SubmissionResponse>> getById(@PathVariable String id) {
        return submissionService.getSubmissionById(id)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @GetMapping("/event/{eventId}")
    public Mono<ApiResponse<List<SubmissionResponse>>> getByEventId(@PathVariable String eventId) {
        return submissionService.getSubmissionsByEventId(eventId)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PostMapping
    public Mono<ApiResponse<SubmissionResponse>> createSubmission(@RequestBody SubmissionCreationRequest request) {
        return submissionService.createSubmission(request)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PostMapping("/{id}")
    public Mono<ApiResponse<SubmissionResponse>> addFeedback(@PathVariable String id, @RequestBody SubmissionFeedbackRequest request) {
        return submissionService.addFeedback(id, request)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
}
