package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClassBySemesterAndYearRequest {
    @JsonProperty("class_code")
    private String classCode;
    private int semester;
    private int year;
}
