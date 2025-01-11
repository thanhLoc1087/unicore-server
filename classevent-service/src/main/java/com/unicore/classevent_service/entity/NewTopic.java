package com.unicore.classevent_service.entity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.TopicStatus;
import com.unicore.classevent_service.enums.TopicType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class NewTopic {
    @Id
    private String id;
    private String name;
    private String description;
    
    @JsonProperty("teacher_codes")
    private List<String> teacherCodes;

    @JsonProperty("teacher_names")
    private List<String> teacherNames;

    @Builder.Default
    private TopicStatus status = TopicStatus.APPROVED;

    TopicType type; 
}
