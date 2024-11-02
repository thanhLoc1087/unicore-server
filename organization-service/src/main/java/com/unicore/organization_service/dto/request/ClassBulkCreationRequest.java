package com.unicore.organization_service.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ClassBulkCreationRequest {
    @JsonProperty("organization_id")
    private String organizationId;
    private List<ClassCreationRequest> classes;
}
