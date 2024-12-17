package com.unicore.organization_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.organization_service.dto.request.OrganizationCreationRequest;
import com.unicore.organization_service.dto.response.ApiResponse;
import com.unicore.organization_service.dto.response.OrganizationResponse;
import com.unicore.organization_service.dto.response.SubjectResponse;
import com.unicore.organization_service.service.OrganizationService;
import com.unicore.organization_service.service.SubjectService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;
    private final SubjectService subjectService;

    @PostMapping
    public Mono<ApiResponse<OrganizationResponse>> createOrg(@RequestBody OrganizationCreationRequest request) {
        return organizationService.createOrg(request)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                LocalDateTime.now()
            ));
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<OrganizationResponse>> getOrgById(@PathVariable String id) {
        return organizationService.getOrgById(id)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                LocalDateTime.now()
            ));
    }

    @GetMapping
    public Mono<ApiResponse<List<OrganizationResponse>>> getAllOrgs() {
        return organizationService.getOrgs()
            .collectList()
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }

    @GetMapping("/{id}/subjects")
    public Mono<ApiResponse<List<SubjectResponse>>> getOrgSubjects(@PathVariable String id) {
        return subjectService.getSubjectsByOrg(id)
            .collectList()
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }   
}
