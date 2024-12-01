package com.unicore.classevent_service.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.WeightType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GeneralTestResponse extends BaseEventResponse {
    private String place;
    private LocalDateTime time;

    @JsonProperty("weight_type")
    private WeightType weightType;

    @Override
    public EventType getEventType() {
        return EventType.TEST;
    }
    
}
