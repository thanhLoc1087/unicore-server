package com.unicore.profile_service.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.profile_service.dto.request.MemberBulkDeletionRequest;
import com.unicore.profile_service.dto.request.TeacherUpdateRequest;
import com.unicore.profile_service.dto.request.TeacherBulkCreationRequest;
import com.unicore.profile_service.dto.request.TeacherCreationRequest;
import com.unicore.profile_service.dto.response.ApiResponse;
import com.unicore.profile_service.dto.response.TeacherResponse;
import com.unicore.profile_service.service.TeacherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public Mono<ApiResponse<TeacherResponse>> createTeacher(@RequestBody @Valid TeacherCreationRequest request) {
        return teacherService.createTeacher(request)
            .map(response -> ApiResponse.<TeacherResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }
    
    @PostMapping("/bulk")
    public Mono<ApiResponse<List<TeacherResponse>>> createTeachers(@RequestBody @Valid TeacherBulkCreationRequest request) {
        return teacherService.createTeachers(request)
            .collectList()
            .map(response -> ApiResponse.<List<TeacherResponse>>builder()
                .data(response)
                .message(response.isEmpty() ? "Empty list" : "Success")
                .build()
            );
    }
    
    @PostMapping("/emails")
    public Mono<ApiResponse<List<TeacherResponse>>> getTeachersFromEmails(@RequestBody List<String> emails) {
        return teacherService.getTeacherByEmails(Set.copyOf(emails))
            .collectList()
            .map(response -> ApiResponse.<List<TeacherResponse>>builder()
                .data(response)
                .message(response.isEmpty() ? "Empty list" : "Success")
                .build()
            );
    }
    
    @PostMapping("/codes")
    public Mono<ApiResponse<List<TeacherResponse>>> getTeachersFromCodes(@RequestBody List<String> codes) {
        return teacherService.getTeacherByCodes(Set.copyOf(codes))
            .collectList()
            .map(response -> ApiResponse.<List<TeacherResponse>>builder()
                .data(response)
                .message(response.isEmpty() ? "Empty list" : "Success")
                .build()
            );
    }
    
    @GetMapping
    public Mono<ApiResponse<List<TeacherResponse>>> getTeachers() {
        return teacherService.getTeachers()
            .collectList()
            .map(response -> ApiResponse.<List<TeacherResponse>>builder()
                .data(response)
                .message(response.isEmpty() ? "Empty list" : "Success")
                .build()
            );
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<TeacherResponse>> getTeacherById(@PathVariable String id) {
        return teacherService.getTeacherById(id)
            .map(response -> ApiResponse.<TeacherResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }

    @PutMapping
    public Mono<ApiResponse<TeacherResponse>> updateTeacher(@RequestBody TeacherUpdateRequest request) {
        return teacherService.updateTeacher(request)
            .map(response -> ApiResponse.<TeacherResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }

    @PutMapping("/bulk")
    public Mono<ApiResponse<List<TeacherResponse>>> updateTeachersBulk(@RequestBody List<TeacherUpdateRequest> requests) {
        return teacherService.updateTeacherBulks(requests)
            .collectList()
            .map(response -> ApiResponse.<List<TeacherResponse>>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }

    @DeleteMapping("/{id}")
    public Mono<ApiResponse<TeacherResponse>> deleteTeacher(@PathVariable String id) {
        return teacherService.deleteById(id)
            .map(response -> ApiResponse.<TeacherResponse>builder()
                .data(response)
                .message("Success")
                .build()
        );
    }

    @DeleteMapping("/bulk")
    public Mono<ApiResponse<List<TeacherResponse>>> deleteTeachersBulk(@RequestBody MemberBulkDeletionRequest request) {
        return teacherService.deleteByIds(request.getIds())
            .collectList()
            .map(response -> ApiResponse.<List<TeacherResponse>>builder()
                .data(response)
                .message("Success")
                .build()
        );
    }
}
