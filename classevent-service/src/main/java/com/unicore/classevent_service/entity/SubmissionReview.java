package com.unicore.classevent_service.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

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
    private String submitterId;
    private String nameId;

    private int attempt;

    private String classId;
    private String subclassCode;

    private Float grade;
    private String feedback;
    private Date feedbackDate;
    private String reviewerId;

    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
}
