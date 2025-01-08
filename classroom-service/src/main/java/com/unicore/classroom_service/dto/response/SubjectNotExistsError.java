package com.unicore.classroom_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectNotExistsError {
    @JsonProperty("class_code")
    private String classCode;
    @JsonProperty("subject_code")
    private String subjectCode;
    @JsonProperty("subject_name")
    private String subjectName;
}
