package com.unicore.organization_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.organization_service.dto.request.SubjectCreationRequest;
import com.unicore.organization_service.dto.response.SubjectResponse;
import com.unicore.organization_service.service.SubjectService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Mono<SubjectResponse>> createSubject(@RequestBody SubjectCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(subjectService.createSubject(request));
    }
    
    @GetMapping
    public ResponseEntity<Flux<SubjectResponse>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getSubjects());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Mono<SubjectResponse>> getSubject(@PathVariable String id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteById(@PathVariable String id) {
        return ResponseEntity.ok(subjectService.deleteById(id));
    }
}
