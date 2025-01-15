package com.unicore.classevent_service.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StudentGradeDetail {
    @JsonProperty("student_code")
    private String studentCode;
    @JsonProperty("student_name")
    private String studentName;
    private List<StudentClassGradeDetailByType> coursework = new ArrayList<>();
    private List<StudentClassGradeDetailByType> practicals = new ArrayList<>();
    private List<StudentClassGradeDetailByType> midterms = new ArrayList<>();
    private List<StudentClassGradeDetailByType> finals = new ArrayList<>();
}
