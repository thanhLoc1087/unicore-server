package com.unicore.organization_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unicore.organization_service.dto.request.OrganizationCreationRequest;
import com.unicore.organization_service.dto.response.OrganizationResponse;
import com.unicore.organization_service.entity.Organization;
import com.unicore.organization_service.mapper.OrganizationMapper;
import com.unicore.organization_service.repository.OrganizationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    public OrganizationResponse createOrg(OrganizationCreationRequest request) {
        Organization org = organizationMapper.toOrganization(request);
        org = organizationRepository.save(org);
        return organizationMapper.toOrganizationResponse(org);
    }

    public OrganizationResponse getOrgById(String id) {
        final Organization org = organizationRepository.findById(id).orElseThrow();
        return organizationMapper.toOrganizationResponse(org);
    }

    public List<OrganizationResponse> getOrgs() {
        return organizationRepository.findAll().stream()
                .map(organizationMapper::toOrganizationResponse)
                .toList();
    }
}
