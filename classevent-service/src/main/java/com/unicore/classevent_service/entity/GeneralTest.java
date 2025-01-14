package com.unicore.classevent_service.entity;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.unicore.classevent_service.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GeneralTest extends BaseEvent {
    {
        setType(EventType.TEST);
    }
    private String place;
    private LocalDate date;
    private boolean autocreated;
}
