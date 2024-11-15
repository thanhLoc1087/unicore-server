package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;

import com.unicore.classevent_service.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEvent extends BaseEntity {
    protected String classId;
    protected String subclassCode;
    protected String name;
    protected String description;
    protected boolean allowGradeReview;
    protected int reviewTimes;
    // người chấm
    protected String graderCode;

    protected LocalDateTime publishDate;
    
    protected EventType type;
}
