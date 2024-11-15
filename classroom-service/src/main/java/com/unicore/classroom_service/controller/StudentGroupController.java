package com.unicore.classroom_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classroom_service.dto.request.StudentGroupingAddGroupRequest;
import com.unicore.classroom_service.dto.request.StudentGroupingCreationRequest;
import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.request.StudentGroupingUpdateRequest;
import com.unicore.classroom_service.dto.response.StudentGroupingResponse;
import com.unicore.classroom_service.service.StudentGroupingService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/grouping")
public class StudentGroupController {
    private final StudentGroupingService studentGroupingService;

    @GetMapping
    public Mono<StudentGroupingResponse> getClassGroup(@RequestBody GetByClassRequest request) {
        return studentGroupingService.getGrouping(request);
    }
    
    @PostMapping()
    public Mono<StudentGroupingResponse> createGrouping(@RequestBody StudentGroupingCreationRequest request) {
        return studentGroupingService.createStudentGrouping(request);
    }

    @PostMapping("/add-group")
    public Mono<StudentGroupingResponse> addGroup(@RequestBody StudentGroupingAddGroupRequest request) {        
        return studentGroupingService.addGroupToStudentGrouping(request);
    }
    
    @PostMapping("/edit")
    public Mono<StudentGroupingResponse> updateStudentGrouping(@RequestBody StudentGroupingUpdateRequest request) {
        return studentGroupingService.updateStudentGrouping(request);
    }
    
}
