package com.unicore.profile_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;

@Data
public class StaffBulkCreationRequest {
    @JsonProperty("organization_id")
    private String organizationId;
    private List<StaffCreationRequest> staff;
}
