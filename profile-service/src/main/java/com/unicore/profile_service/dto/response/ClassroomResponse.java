package com.unicore.profile_service.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.profile_service.enums.ClassType;

import lombok.Data;

@Data
public class ClassroomResponse {
    private String id;

    @JsonProperty("organization_id")
    private String organizationId;
    
    private String code;
    
    @JsonProperty("subject_code")
    private String subjectCode;
    @JsonProperty("subject_name")
    private String subjectName;

    @JsonProperty("org_managed")
    private boolean orgManaged;
    
    private int semester;
    private int year;

    private ClassType type;
    
    private List<Subclass> subclasses;
}