package com.unicore.profile_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.profile_service.dto.request.GetStudentListByClass;
import com.unicore.profile_service.dto.request.MemberBulkDeletionRequest;
import com.unicore.profile_service.dto.request.StudentBulkCreationRequest;
import com.unicore.profile_service.dto.request.StudentCreationRequest;
import com.unicore.profile_service.dto.request.StudentUpdateRequest;
import com.unicore.profile_service.dto.response.ApiResponse;
import com.unicore.profile_service.dto.response.StudentInClassResponse;
import com.unicore.profile_service.dto.response.StudentResponse;
import com.unicore.profile_service.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    
    private final StudentService studentService;

    @PostMapping
    public Mono<ApiResponse<StudentResponse>> createStudent(@RequestBody @Valid StudentCreationRequest request) {
        return studentService.createStudent(request)
            .map(response -> ApiResponse.<StudentResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }
    
    @PostMapping("/bulk")
    public Mono<ApiResponse<List<StudentResponse>>> createStudents(@RequestBody @Valid StudentBulkCreationRequest request) {
        log.info(request.toString());
        return studentService.createStudents(request)
            .collectList()
            .map(response -> ApiResponse.<List<StudentResponse>>builder()
                .data(response)
                .message(response.isEmpty() ? "Empty list" : "Success")
                .build()
            );
    }
    
    @GetMapping
    public Mono<ApiResponse<List<StudentResponse>>> getStudents() {
        return studentService.getStudents()
            .collectList()
            .map(response -> ApiResponse.<List<StudentResponse>>builder()
                .data(response)
                .message(response.isEmpty() ? "Empty list" : "Success")
                .build()
            );
    }

    @PostMapping("/class")
    public Mono<ApiResponse<StudentInClassResponse>> getStudentsInClass(@RequestBody GetStudentListByClass request) {
        return studentService.getStudentsInClass(request)
            .map(response -> ApiResponse.<StudentInClassResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }
    
    @GetMapping("/{id}")
    public Mono<ApiResponse<StudentResponse>> getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id)
            .map(response -> ApiResponse.<StudentResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }

    @PutMapping
    public Mono<ApiResponse<StudentResponse>> updateStudent(@RequestBody StudentUpdateRequest request) {
        return studentService.updateStudent(request)
            .map(response -> ApiResponse.<StudentResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }

    @PutMapping("/bulk")
    public Mono<ApiResponse<List<StudentResponse>>> updateStudentsBulk(@RequestBody List<StudentUpdateRequest> requests) {
        return studentService.updateStudentBulks(requests)
            .collectList()
            .map(response -> ApiResponse.<List<StudentResponse>>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }

    @DeleteMapping("/{id}")
    public Mono<ApiResponse<StudentResponse>> deleteStudent(@PathVariable String id) {
        return studentService.deleteById(id)
            .map(response -> ApiResponse.<StudentResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }

    @DeleteMapping("/bulk")
    public Mono<ApiResponse<List<StudentResponse>>> deleteStudentsBulk(@RequestBody MemberBulkDeletionRequest request) {
        return studentService.deleteByIds(request.getIds())
            .collectList()
            .map(response -> ApiResponse.<List<StudentResponse>>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }

    @GetMapping("/code/{code}")
    public Mono<ApiResponse<StudentResponse>> getByCode(@PathVariable String code) {
        return studentService.getStudentByCode(code)
            .map(response -> ApiResponse.<StudentResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }
    
}
