package com.unicore.organization_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationCreationRequest {
    private String name;
    private String description;
    private String imageUrl;
}
