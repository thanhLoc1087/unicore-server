package com.unicore.organization_service.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.organization_service.enums.ClassType;

import lombok.Data;

@Data
public class ClassroomResponse {
    private String id;
    @JsonProperty("organization_id")
    private String organizationId;
    
    private String code;
    
    @JsonProperty("subject_code")
    private String subjectCode;
    
    private int credits;
    @JsonProperty("is_org_managed")
    private boolean isOrgManaged;
    
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;

    
    @JsonProperty("teacher_code")
    private String teacherCode;

    @JsonProperty("teacher_name")
    private String teacherName;

    private ClassType type;
    
    private int semester;
    private int year;
    private String note;
}
