package com.unicore.classroom_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckStudentClassForGroupingRequest {
    @JsonProperty("student_code")
    private String studentCode;
    @JsonProperty("teacher_code")
    private String teacherCode;
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;
}
