package com.unicore.profile_service.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TeacherBulkCreationRequest {
    @JsonProperty("organization_id")
    private String organizationId;
    private List<TeacherCreationRequest> teachers;
}
