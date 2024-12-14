package com.unicore.classevent_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.SubmissionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Report extends BaseEvent {

    // Nếu nó tạo trong BTL
    @JsonProperty("project_id")
    private String projectId;

    private String room;

    private LocalDateTime publishDate;

    private SubmissionOption submissionOption;

    private LocalDate date;

    private LocalDate remindGradingDate;
    private LocalDate closeSubmissionDate;

    private String attachmentUrl;
    
    private Query query;
}
