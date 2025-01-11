package com.unicore.classevent_service.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternTopic extends NewTopic {
    private String companyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Float companyGrade;
    
    private String councilCode;
    private TopicGradingMember councilPrincipal;
    private TopicGradingMember councilSecretary;
    private TopicGradingMember councilMember;
    private boolean councilGraded;
}
