package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.unicore.classevent_service.enums.SubmissionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
    @Id
    private String id;
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;

    private String classId;
    private String subclassCode;
    private String name;
    private String description;
    private String place;
    private boolean allowGradeReview;
    private int reviewTimes;
    // người chấm
    private String graderCode;

    private LocalDateTime publishDate;

    private boolean inGroup;
    private SubmissionOption submissionOption;

    private Float weight;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime remindGradingDate;
    private LocalDateTime closeSubmissionDate;

    private String attachmentUrl;
    
    private Query query;
}
