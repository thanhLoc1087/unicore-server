package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.Group;
import com.unicore.classevent_service.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventGroupingAddGroupRequest {
    @JsonProperty("event_id")
    String eventId;
    EventType eventType;
    Group group;
}
