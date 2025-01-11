package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClassGroupingRequest {
    @JsonProperty("class_id")
    private String classId;
    
    @JsonProperty("subclass_code")
    private String subclassCode;

    @JsonProperty("grouping_id")
    private String groupingId;
}

