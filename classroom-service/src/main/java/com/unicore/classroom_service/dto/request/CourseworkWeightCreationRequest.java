package com.unicore.classroom_service.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.entity.CourseworkWeightDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseworkWeightCreationRequest {
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;
    
    private List<CourseworkWeightDetail> details;
}
