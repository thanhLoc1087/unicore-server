package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.ClassType;
import com.unicore.classevent_service.enums.ExamFormat;
import com.unicore.classevent_service.enums.WeightType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralTestCreationRequest {
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("class_type")
    private ClassType classType;
    @JsonProperty("subclass_code")
    private String subclassCode;
    @JsonProperty("weight_type")
    private WeightType weightType;
    private Float weight;

    private ExamFormat format;
}
