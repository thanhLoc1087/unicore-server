package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.SubmissionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Homework extends BaseEvent {
    {
        setType(EventType.HOMEWORK);
    }

    private LocalDateTime publishDate;

    private SubmissionOption submissionOption;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime remindGradingDate;
    private LocalDateTime closeSubmissionDate;

    private String attachmentUrl;
}
