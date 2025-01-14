package com.unicore.classroom_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherResponse {
    private String id;
    @JsonProperty("organization_id")
    private String organizationId;
    private String code;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String degree;
    @JsonProperty("research_direction")
    private String researchDirection;
    @JsonProperty("research_concern")
    private String researchConcern;
    private boolean deleted;
}
