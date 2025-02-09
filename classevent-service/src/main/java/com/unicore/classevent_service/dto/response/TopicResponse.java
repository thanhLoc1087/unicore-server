package com.unicore.classevent_service.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.TopicStatus;
import com.unicore.classevent_service.enums.TopicType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicResponse {
    private String id;
    private String name;
    private String description;
    private String note;
    
    @JsonProperty("teacher_mails")
    private List<String> teacherMails;
    
    @JsonProperty("teacher_codes")
    private List<String> teacherCodes;

    @JsonProperty("teacher_names")
    private List<String> teacherNames;

    private TopicStatus status;

    private TopicType type; 
   
}
