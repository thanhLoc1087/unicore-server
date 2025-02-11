package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;
import java.util.List;

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

    private List<SubmissionOption> submissionOptions;

    private LocalDateTime remindGradingDate;
    private LocalDateTime closeSubmissionDate;

    private String attachmentUrl;

    private int commentCount;
}
