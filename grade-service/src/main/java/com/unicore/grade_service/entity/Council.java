package com.unicore.grade_service.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Council {
    private String classId;
    private GradingMember assignedStaff;
    private GradingMember secretary;
    private GradingMember principal;
    private GradingMember delegate;

    private List<String> assignedTopics;
}
