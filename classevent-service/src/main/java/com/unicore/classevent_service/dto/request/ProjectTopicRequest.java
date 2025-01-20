package com.unicore.classevent_service.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTopicRequest {
    private String name;
    private String description;
    private String note;
    
    @JsonProperty("teacher_mails")
    private List<String> teacherMails;
    
    @JsonProperty("teacher_codes")
    private List<String> teacherCodes;

    @JsonProperty("teacher_names")
    private List<String> teacherNames;

    private String nameEn;
    private String projectId;

    @JsonProperty("student_id")
    private String studentId;

    private ProjectGroupRequest groupRequest;
    
}
