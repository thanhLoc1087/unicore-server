package com.unicore.classevent_service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.NewTopic;
import com.unicore.classevent_service.entity.ProjectTopic;
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
    @JsonProperty("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonProperty("allow_topic_suggestion")
    private boolean allowTopicSuggestion;

    @JsonProperty("start_topic_register_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTopicRegisterTime;

    @JsonProperty("end_topic_register_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTopicRegisterTime;

    private List<NewTopic> topics;

    @Override
    public EventType getEventType() {
        return EventType.PROJECT;
    }
}
