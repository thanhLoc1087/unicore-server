package com.unicore.classevent_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetClassGradeRequest;
import com.unicore.classevent_service.dto.request.GetStudentClassGradeRequest;
import com.unicore.classevent_service.dto.request.ReviewCreationRequest;
import com.unicore.classevent_service.dto.request.ReviewFeedbackRequest;
import com.unicore.classevent_service.dto.request.SubmissionCreationRequest;
import com.unicore.classevent_service.dto.request.SubmissionFeedbackRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.StudentClassGradeResponse;
import com.unicore.classevent_service.dto.response.StudentGradeDetail;
import com.unicore.classevent_service.dto.response.SubmissionResponse;
import com.unicore.classevent_service.dto.response.SubmissionReviewResponse;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.enums.ReviewStatus;
import com.unicore.classevent_service.service.SubmissionReviewService;
import com.unicore.classevent_service.service.SubmissionService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;
    private final SubmissionReviewService submissionReviewService;

    // Lấy 1 bài nộp bằng id
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

    // Lấy ds bài nộp theo event 
    @GetMapping("/event/{eventId}")
    public Mono<ApiResponse<List<SubmissionResponse>>> getByEventId(@PathVariable String eventId) {
        return submissionService.getSubmissionsByEventId(eventId)
            .collectList()
            .map(response -> new ApiResponse<>(
                response, 
                response.isEmpty() ? "Empty List" : ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    // Tạo bài nộp
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
    
    // Chám điểm
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
    
    @PostMapping("/review")
    public Mono<ApiResponse<SubmissionReviewResponse>> createSubmissionReview(@RequestBody ReviewCreationRequest request) {
        return submissionReviewService.createSubmissionReview(request)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
        
    @PutMapping("/review/{id}")
    public Mono<ApiResponse<SubmissionReviewResponse>> feedbackSubmissionReview(@PathVariable String id, @RequestBody ReviewFeedbackRequest request) {
        return submissionReviewService.feedbackSubmissionReview(id, request)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
        }

    @PutMapping("/review/{id}/turn-down")
    public Mono<ApiResponse<SubmissionReviewResponse>> turnDownSubmissionReview(@PathVariable String id) {
        return submissionReviewService.turnDownSubmissionReview(id)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
        }
        
    @GetMapping("/review/reviewer/{reviewerId}")
    public Mono<ApiResponse<List<SubmissionReviewResponse>>> getByReviewerId(@PathVariable String reviewerId) {
        return submissionReviewService.getSubmissionReviewByReviewer(reviewerId)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @GetMapping("/review/reviewer/{reviewerId}/{status}")
    public Mono<ApiResponse<List<SubmissionReviewResponse>>> getByReviewerIdAndStatus(
        @PathVariable String reviewerId, 
        @PathVariable ReviewStatus status
    ) {
        return submissionReviewService.getSubmissionReviewByReviewerAndStatus(reviewerId, status)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PostMapping("/review/class")
    public Mono<ApiResponse<List<SubmissionReviewResponse>>> getByClass(@RequestBody GetByClassRequest request) {
        return submissionReviewService.getSubmissionReviewByClass(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PostMapping("/grade/class")
    public Mono<ApiResponse<List<StudentClassGradeResponse>>> getClassStudentGrades(@RequestBody GetClassGradeRequest request) {
        return submissionService.getStudentClassGrade(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PostMapping("/grade")
    public Mono<ApiResponse<StudentGradeDetail>> getStudentGrade(@RequestBody GetStudentClassGradeRequest request) {
        return submissionService.getStudentClassGradeDetail(request)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
}
