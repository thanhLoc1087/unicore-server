package com.unicore.classevent_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentClassGradeResponse {
    private String studentCode;
    private String studentName;
    private Float coursework;
    private Float practical;
    private Float midterm;
    private Float finalGrade;
    private Float average;
}
