package com.unicore.classroom_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.entity.SubjectMetadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectClientResponse {
    private String id;
    private String name;
    private String code;
    private String description;
    @JsonProperty("organization_id")
    private String organizationId;
    private SubjectMetadata metadata;
}
