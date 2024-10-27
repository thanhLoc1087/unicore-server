package com.unicore.organization_service.service;

import org.springframework.stereotype.Service;

import com.unicore.common_service.exception.AppException;
import com.unicore.common_service.exception.ErrorCode;
import com.unicore.organization_service.dto.request.OrganizationCreationRequest;
import com.unicore.organization_service.dto.response.OrganizationResponse;
import com.unicore.organization_service.entity.Organization;
import com.unicore.organization_service.mapper.OrganizationMapper;
import com.unicore.organization_service.repository.OrganizationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    public Mono<OrganizationResponse> createOrg(OrganizationCreationRequest request) {
        Organization org = organizationMapper.toOrganization(request);
        log.info(org.toString());
        return createNewOrg(org);
    }
    
    public Mono<OrganizationResponse> createNewOrg(Organization request) {
        return Mono.just(request)
            .flatMap(organizationRepository::save)
            .map(organizationMapper::toOrganizationResponse)
            .doOnError(throwable -> log.error(throwable.getMessage(), throwable))
            .doOnSuccess(response -> {
            });
    }

    public Mono<OrganizationResponse> getOrgById(String id) {
        return organizationRepository.findById(id)
            .map(organizationMapper::toOrganizationResponse)
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.UNCATEGORIZED)));
    }

    public Flux<OrganizationResponse> getOrgs() {
        return organizationRepository.findAll()
                .map(organizationMapper::toOrganizationResponse)
                .switchIfEmpty(Mono.error(new Exception("Organization list empty")));
    }
}
