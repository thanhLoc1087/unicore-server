package com.unicore.organization_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponse {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
}
