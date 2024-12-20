package com.unicore.profile_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.profile_service.dto.request.MemberBulkDeletionRequest;
import com.unicore.profile_service.dto.request.StaffBulkCreationRequest;
import com.unicore.profile_service.dto.request.StaffCreationRequest;
import com.unicore.profile_service.dto.response.ApiResponse;
import com.unicore.profile_service.dto.response.StaffResponse;
import com.unicore.profile_service.service.StaffService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {
    
    private final StaffService staffService;

    @PostMapping
    public Mono<ApiResponse<StaffResponse>> createStaff(@RequestBody @Valid StaffCreationRequest request) {
        return staffService.createStaff(request)
            .map(response -> ApiResponse.<StaffResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
    }
    
    @PostMapping("/bulk")
    public Mono<ApiResponse<List<StaffResponse>>> createStaffs(@RequestBody @Valid StaffBulkCreationRequest request) {
        return staffService.createStaffs(request)
            .collectList()
            .map(response -> ApiResponse.<List<StaffResponse>>builder()
                .data(response)
                .message(response.isEmpty() ? "Empty list" : "Success")
                .build()
            );
    }
    
    @GetMapping
    public Mono<ApiResponse<List<StaffResponse>>> getStaffs() {
        return staffService.getStaffs()
            .collectList()
            .map(response -> ApiResponse.<List<StaffResponse>>builder()
                .data(response)
                .message(response.isEmpty() ? "Empty list" : "Success")
                .build()
            );
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<StaffResponse>> getStaffById(@PathVariable String id) {
        return staffService.getStaffById(id)
            .map(response -> ApiResponse.<StaffResponse>builder()
                .data(response)
                .message("Success")
                .build()
            );
        }
        
    @DeleteMapping("/bulk")
    public Mono<ApiResponse<String>> deleteStaffBulk(MemberBulkDeletionRequest request) {
        return staffService.deleteByIds(request.getIds())
            .then(Mono.just(ApiResponse.<String>builder()
                .data(HttpStatus.OK.toString())
                .message("Success")
                .build()
            ));
    }
}
