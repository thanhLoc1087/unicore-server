package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;

import com.unicore.classevent_service.enums.SubmissionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Homework extends BaseEvent {
    // Nếu nó tạo trong BTL
    private String projectId;

    private LocalDateTime publishDate;

    private SubmissionOption submissionOption;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime remindGradingDate;
    private LocalDateTime closeSubmissionDate;

    private String attachmentUrl;
}
