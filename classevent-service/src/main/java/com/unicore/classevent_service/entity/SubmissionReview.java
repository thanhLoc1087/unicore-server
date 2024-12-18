package com.unicore.classevent_service.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.unicore.classevent_service.enums.ReviewStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionReview {
    private String id;

    private String submissionId;
    private String eventId;
    private String eventName;
    private String submitterId;
    private String submitterName;

    private int attempt;

    private String classId;
    private String subclassCode;

    @Builder.Default
    private List<Float> grades = new ArrayList<>();
    @Builder.Default
    private List<String> feedbacks = new ArrayList<>();
    @Builder.Default
    private List<Date> feedbackDates = new ArrayList<>();
    private String reviewerId;

    private String turnDownReason;

    @Builder.Default
    private ReviewStatus status = ReviewStatus.UNPROCESSED;

    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
}
