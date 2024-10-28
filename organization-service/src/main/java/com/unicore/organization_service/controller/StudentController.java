package com.unicore.organization_service.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.organization_service.dto.request.MemberBulkDeletionRequest;
import com.unicore.organization_service.dto.request.StudentBulkCreationRequest;
import com.unicore.organization_service.dto.request.StudentCreationRequest;
import com.unicore.organization_service.dto.response.StudentResponse;
import com.unicore.organization_service.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    
    private final StudentService studentService;

    @PostMapping
    public Mono<StudentResponse> createStudent(@RequestBody @Valid StudentCreationRequest request) {
        return studentService.createStudent(request);
    }
    
    @PostMapping("/bulk")
    public Flux<StudentResponse> createStudents(@RequestBody @Valid StudentBulkCreationRequest request) {
        log.info(request.toString());
        return studentService.createStudents(request);
    }
    
    @GetMapping
    public Flux<StudentResponse> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public Mono<StudentResponse> getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/bulk")
    public Mono<Void> deleteStudentsBulk(MemberBulkDeletionRequest request) {
        return studentService.deleteByIds(request.getIds());
    }
}
