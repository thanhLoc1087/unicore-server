package com.unicore.classroom_service.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.request.StudentGroupingAddGroupRequest;
import com.unicore.classroom_service.dto.request.StudentGroupingCreationRequest;
import com.unicore.classroom_service.dto.request.StudentGroupingUpdateRequest;
import com.unicore.classroom_service.dto.response.ApiResponse;
import com.unicore.classroom_service.dto.response.StudentGroupingResponse;
import com.unicore.classroom_service.service.StudentGroupingService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grouping")
public class StudentGroupController {
    private final StudentGroupingService studentGroupingService;

    @PostMapping("/get")
    public Mono<ApiResponse<StudentGroupingResponse>> getClassGroup(@RequestBody GetByClassRequest request) {
        return studentGroupingService.getGrouping(request)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                LocalDateTime.now()
            ));
    }
    
    @PostMapping()
    public Mono<ApiResponse<StudentGroupingResponse>> createGrouping(@RequestBody StudentGroupingCreationRequest request) {
        return studentGroupingService.createStudentGrouping(request)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }

    @PostMapping("/add-group")
    public Mono<ApiResponse<StudentGroupingResponse>> addGroup(@RequestBody StudentGroupingAddGroupRequest request) {        
        return studentGroupingService.addGroupToStudentGrouping(request)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }
    
    @PostMapping("/edit")
    public Mono<ApiResponse<StudentGroupingResponse>> updateStudentGrouping(@RequestBody StudentGroupingUpdateRequest request) {
        return studentGroupingService.updateStudentGrouping(request)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }
    
}
