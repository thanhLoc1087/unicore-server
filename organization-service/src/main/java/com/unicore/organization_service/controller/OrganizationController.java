package com.unicore.organization_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.organization_service.dto.request.OrganizationCreationRequest;
import com.unicore.organization_service.dto.response.OrganizationResponse;
import com.unicore.organization_service.service.OrganizationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<Mono<OrganizationResponse>> createOrg(@RequestBody OrganizationCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(organizationService.createOrg(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<OrganizationResponse>> getOrgById(@PathVariable String id) {
        return ResponseEntity.ok(organizationService.getOrgById(id));
    }

    @GetMapping
    public ResponseEntity<Flux<OrganizationResponse>> getAllOrgs() {
        return ResponseEntity.ok(organizationService.getOrgs());
    }
}
