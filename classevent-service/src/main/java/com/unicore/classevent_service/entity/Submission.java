package com.unicore.classevent_service.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private List<StudentInSubmission> submitters;
    private boolean inGroup;
    
    private Float grade;
    private String feedback;
    private LocalDateTime feedbackDate;
    private String reviewerId;

    private String attachmentId;
    private String attachmentName;
    private String attachmentUrl;

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
