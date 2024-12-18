package com.unicore.organization_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.organization_service.dto.request.SubjectBulkCreationRequest;
import com.unicore.organization_service.dto.request.SubjectCreationRequest;
import com.unicore.organization_service.dto.response.ApiResponse;
import com.unicore.organization_service.dto.response.SubjectInListResponse;
import com.unicore.organization_service.dto.response.SubjectResponse;
import com.unicore.organization_service.service.SubjectService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public Mono<ApiResponse<SubjectResponse>> createSubject(@RequestBody SubjectCreationRequest request) {
        return subjectService.createSubject(request)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }

    @PostMapping("/bulk")
    public Mono<ApiResponse<List<SubjectInListResponse>>> createSubject(@RequestBody SubjectBulkCreationRequest request) {
        return subjectService.createSubjects(request)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }
    
    @GetMapping
    public Mono<ApiResponse<List<SubjectInListResponse>>> getAllSubjects() {
        return subjectService.getSubjects()
            .collectList()
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }
    
    @GetMapping("/{id}")
    public Mono<ApiResponse<SubjectResponse>> getSubject(@PathVariable String id) {
        return subjectService.getSubjectById(id)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }
    
    @GetMapping("/code/{code}")
    public Mono<ApiResponse<SubjectResponse>> getSubjectByCode(@PathVariable String code) {
        return subjectService.getSubjectByCode(code)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }

    @DeleteMapping("/{id}")
    public Mono<ApiResponse<Void>> deleteById(@PathVariable String id) {
        return subjectService.deleteById(id)
            .then(Mono.just(new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                null,
                LocalDateTime.now()
            )));
    }
}
