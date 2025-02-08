package com.unicore.classevent_service.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.unicore.classevent_service.dto.response.FileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Submission {
    @Id
    private String id;
    
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;

    private String eventId;

    private String submitter;
    private boolean inGroup;
    
    private Float grade;
    private String feedback;
    private LocalDateTime feedbackDate;

    @Builder.Default
    private Map<String, Float> memberGrades = new HashMap<>();

    private String submissionLink;

    @Builder.Default
    private List<FileResponse> files = new ArrayList<>();

    private String submitTimeStatus;

    public String calculateSubmissionTime(LocalDateTime dueDate) {
        Duration duration = Duration.between(createdDate, dueDate);
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();

        StringBuilder result = new StringBuilder();

        if (duration.isNegative()) {
            result.append("Bài nộp trễ ");
            days = Math.abs(days);
            hours = Math.abs(hours);
            minutes = Math.abs(minutes);
        } else {
            result.append("Bài nộp sớm ");
        }

        if (days > 0) {
            result.append(days).append(" ngày");
        }
        if (hours > 0) {
            if (result.length() > 0) result.append(" ");
            result.append(hours).append(" giờ");
        }
        if (minutes > 0) {
            if (result.length() > 0) result.append(" ");
            result.append(minutes).append(" phút");
        }

        return result.toString();
    }
}
