package com.unicore.profile_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.profile_service.dto.request.MemberBulkDeletionRequest;
import com.unicore.profile_service.dto.request.TeacherBulkCreationRequest;
import com.unicore.profile_service.dto.request.TeacherCreationRequest;
import com.unicore.profile_service.dto.response.TeacherResponse;
import com.unicore.profile_service.service.TeacherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public Mono<TeacherResponse> createTeacher(@RequestBody @Valid TeacherCreationRequest request) {
        return teacherService.createTeacher(request);
    }
    
    @PostMapping("/bulk")
    public Flux<TeacherResponse> createTeachers(@RequestBody @Valid TeacherBulkCreationRequest request) {
        return teacherService.createTeachers(request);
    }
    
    @GetMapping
    public Flux<TeacherResponse> getTeachers() {
        return teacherService.getTeachers();
    }

    @GetMapping("/{id}")
    public Mono<TeacherResponse> getTeacherById(@PathVariable String id) {
        return teacherService.getTeacherById(id);
    }

    @DeleteMapping("/bulk")
    public Mono<Void> deleteTeachersBulk(MemberBulkDeletionRequest request) {
        return teacherService.deleteByIds(request.getIds());
    }
}
