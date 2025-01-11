package com.unicore.classevent_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProjectTopic extends NewTopic {
    private String nameEn;
    private String projectId;
    private Float supervisorGrade;
    private Group group;
    private String turnDownReason;
}
