package com.unicore.classroom_service.dto.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    
    @JsonProperty("subject_name")
    private String subjectName;
    
    @JsonProperty("is_org_managed")
    private boolean isOrgManaged;

    @JsonProperty("teacher_codes")
    private List<String> teacherCodes = new ArrayList<>();

    @JsonProperty("teacher_names")
    private List<String> teacherNames = new ArrayList<>();

    private ClassType type;

    private int credits;
    
    @JsonProperty("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    
    @JsonProperty("end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    @JsonProperty("max_size")
    private int maxSize;

    @JsonProperty("current_size")
    private int currentSize;
    
    private int semester;
    private int year;

    private String note;
}

