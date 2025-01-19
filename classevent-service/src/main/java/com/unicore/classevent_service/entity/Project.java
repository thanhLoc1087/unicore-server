package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class Project extends BaseEvent {
    {
        setType(EventType.PROJECT);
    }
    
    @JsonProperty("allow_topic_suggestion")
    private boolean allowTopicSuggestion;

    private boolean autocreated;

    private LocalDateTime startTopicRegisterTime;
    private LocalDateTime endTopicRegisterTime;
}
