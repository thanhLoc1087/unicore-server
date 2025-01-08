package com.unicore.classroom_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassFilterRequest {
    private int semester;
    private int year;
    @JsonProperty("subject_code")
    private String subjectCode;
    @JsonProperty("teacher_code")
    private String teacherCode;
}
