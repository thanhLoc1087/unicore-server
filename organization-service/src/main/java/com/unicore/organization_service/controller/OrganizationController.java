package com.unicore.organization_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.common_service.dto.response.ApiResponse;
import com.unicore.organization_service.dto.request.OrganizationCreationRequest;
import com.unicore.organization_service.dto.response.OrganizationResponse;
import com.unicore.organization_service.service.OrganizationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping
    public ApiResponse<OrganizationResponse> createOrg(@RequestBody OrganizationCreationRequest request) {
        log.info("HOLAAAAAAAAAAA");
        return ApiResponse.<OrganizationResponse>builder()
                .result(organizationService.createOrg(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrganizationResponse> getMethodName(@PathVariable String id) {
        return ApiResponse.<OrganizationResponse>builder()
                .result(organizationService.getOrgById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrganizationResponse>> getMethodName() {
        return ApiResponse.<List<OrganizationResponse>>builder()
                .result(organizationService.getOrgs())
                .build();
    }
}
