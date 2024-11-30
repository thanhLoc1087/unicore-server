package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.unicore.classevent_service.enums.WeightType;

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
    private String place;
    private LocalDateTime time;
    private WeightType weightType;
}
