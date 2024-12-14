package com.unicore.classroom_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.enums.ExamFormat;
import com.unicore.classroom_service.enums.WeightType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralTestCreationRequest {
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;
    @JsonProperty("weight_type")
    WeightType weightType;
    
    private Float weight;

    ExamFormat format;
}
