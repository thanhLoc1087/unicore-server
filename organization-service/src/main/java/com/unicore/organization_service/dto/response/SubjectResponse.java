package com.unicore.organization_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.organization_service.entity.SubjectMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
    private String id;
    private String name;
    private String code;
    private String description;
    @JsonProperty("organization_id")
    private String organizationId;
    private SubjectMetadata metadata;
}
