package com.unicore.organization_service.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.unicore.organization_service.enums.ClassType;

import lombok.Data;

@Table
@Data
public class Classroom {
    @Id
    private String id;

    private String organizationId;

    private String code;

    private String subjectCode;
    private String teacherCode;
    private String teacherAssistantCode;

    private int credits;
    private boolean isOrgManaged;

    private LocalDate startDate;
    private LocalDate endDate;

    private ClassType type;
    
    private int semester;
    private int year;

    private String note;
}
