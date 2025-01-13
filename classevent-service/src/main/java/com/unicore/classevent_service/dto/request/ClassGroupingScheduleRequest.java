package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ClassGroupingScheduleRequest extends GroupingScheduleRequest {
    @JsonProperty("class_id")
    private String classId; 
    @JsonProperty("subclass_code")
    private String subclassCode; 
}
