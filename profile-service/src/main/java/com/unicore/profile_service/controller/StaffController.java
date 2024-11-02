package com.unicore.profile_service.controller;

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
import com.unicore.profile_service.dto.response.StaffResponse;
import com.unicore.profile_service.service.StaffService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {
    
    private final StaffService staffService;

    @PostMapping
    public Mono<StaffResponse> createStaff(@RequestBody @Valid StaffCreationRequest request) {
        return staffService.createStaff(request);
    }
    
    @PostMapping("/bulk")
    public Flux<StaffResponse> createStaffs(@RequestBody @Valid StaffBulkCreationRequest request) {
        return staffService.createStaffs(request);
    }
    
    @GetMapping
    public Flux<StaffResponse> getStaffs() {
        return staffService.getStaffs();
    }

    @GetMapping("/{id}")
    public Mono<StaffResponse> getStaffById(@PathVariable String id) {
        return staffService.getStaffById(id);
    }

    @DeleteMapping("/bulk")
    public Mono<Void> deleteStaffBulk(MemberBulkDeletionRequest request) {
        return staffService.deleteByIds(request.getIds());
    }
}
