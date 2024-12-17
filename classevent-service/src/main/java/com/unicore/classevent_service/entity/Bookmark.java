package com.unicore.classevent_service.entity;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
    @Id
    private String id;
    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("event_type")
    private EventType eventYpe;
}
