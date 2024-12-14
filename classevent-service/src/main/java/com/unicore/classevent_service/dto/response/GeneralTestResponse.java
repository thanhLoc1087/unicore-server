package com.unicore.classevent_service.dto.response;

import java.time.LocalDate;

import com.unicore.classevent_service.enums.EventType;

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
    private LocalDate date;

    @Override
    public EventType getEventType() {
        return EventType.TEST;
    }
    
}
