package com.unicore.classevent_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.SubmissionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Report extends BaseEvent {
    {
        setType(EventType.REPORT);
    }

    private String room;

    private LocalDateTime publishDate;

    private List<SubmissionOption> submissionOptions;

    private LocalDate date;
    
    @JsonProperty("project_id")
    // Nếu nó tạo trong BTL
    private String projectId; 

    private LocalDate remindGradingDate;
    private LocalDate closeSubmissionDate;

    private String attachmentUrl;

    @Builder.Default
    private Map<String, Float> grades = new HashMap<>();
    
    private Query query;
}
