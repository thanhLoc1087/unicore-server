package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ClassGroupingScheduleRequest extends GroupingScheduleRequest {
    @JsonIgnoreProperties("class_id")
    private String classId; 
    @JsonIgnoreProperties("subclass_code")
    private String subclassCode; 
}
