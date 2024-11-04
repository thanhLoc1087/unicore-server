package com.unicore.classroom_service.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ClassroomBulkCreationRequest {
    @JsonProperty("organization_id")
    private String organizationId;
    private List<ClassroomCreationRequest> classes;
}
