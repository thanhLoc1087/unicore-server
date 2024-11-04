package com.unicore.classroom_service.dto.response;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.entity.Subclass;

import lombok.Data;

@Data
public class ClassroomResponse {
    private String id;

    @JsonProperty("organization_id")
    private String organizationId;
    
    @Indexed(unique = true)
    private String code;
    
    @JsonProperty("subject_code")
    private String subjectCode;
    
    private int credits;

    @JsonProperty("org_managed")
    private boolean orgManaged;
    
    private List<Subclass> subclasses;
    
    private int semester;
    private int year;
}