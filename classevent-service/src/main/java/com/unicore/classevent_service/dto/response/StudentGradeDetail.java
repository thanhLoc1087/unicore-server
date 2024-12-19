package com.unicore.classevent_service.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StudentGradeDetail {
    @JsonProperty("student_code")
    private String studentCode;
    @JsonProperty("student_name")
    private String studentName;
    private List<StudentClassGradeDetailByType> coursework;
    private List<StudentClassGradeDetailByType> practicals;
    private List<StudentClassGradeDetailByType> midterms;
    private List<StudentClassGradeDetailByType> finals;
}
