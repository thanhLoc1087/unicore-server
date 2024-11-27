package com.unicore.classevent_service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.Topic;
import com.unicore.classevent_service.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProjectResponse extends BaseEventResponse {
    private Float weight;
    
    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("allow_topic_suggestion")
    private boolean allowTopicSuggestion;

    private List<Topic> topics;

    @Override
    public EventType getEventType() {
        return EventType.PROJECT;
    }
}
