package com.unicore.organization_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.unicore.organization_service.dto.request.OrganizationCreationRequest;
import com.unicore.organization_service.dto.response.OrganizationResponse;
import com.unicore.organization_service.entity.Organization;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    @Mapping(target = "id", ignore = true)
    public Organization toOrganization(OrganizationCreationRequest request);

    public OrganizationResponse toOrganizationResponse(Organization organization);
}
