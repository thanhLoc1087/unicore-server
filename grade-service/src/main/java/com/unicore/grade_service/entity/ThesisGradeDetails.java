package com.unicore.grade_service.entity;

import lombok.Data;

@Data
public class ThesisGradeDetails {
    private GradingMember student;
    
    private Float complexity;
    private Float practicality;
    private Float research;
    private Float implementation;
    private Float learning;
    private Float report;
    private Float presentation;
    private Float qna;
    private Float planning;
    private Float problemSolving;
}
