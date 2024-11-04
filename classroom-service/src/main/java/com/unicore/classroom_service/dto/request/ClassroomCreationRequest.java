package com.unicore.classroom_service.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.enums.ClassType;

import lombok.Data;

@Data
public class ClassroomCreationRequest {
    @JsonProperty("organization_id")
    private String organizationId;
    
    private String code;
    
    @JsonProperty("subject_code")
    private String subjectCode;
    
    @JsonProperty("is_org_managed")
    private boolean isOrgManaged;

    @JsonProperty("teacher_code")
    private String teacherCode;

    @JsonProperty("teacher_assistant_code")
    private String teacherAssistantCode;

    @JsonProperty("teacher_name")
    private String teacherName;

    private ClassType type;

    private int credits;
    
    @JsonProperty("start_date")
    private LocalDate startDate;
    
    @JsonProperty("end_date")
    private LocalDate endDate;
    
    private int semester;
    private int year;

    private String note;
}

