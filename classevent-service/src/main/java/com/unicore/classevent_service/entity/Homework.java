package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.unicore.classevent_service.enums.SubmissionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Homework {
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
    private boolean allowGradeReview;
    private int reviewTimes;
    // người chấm
    private String graderCode;

    private LocalDateTime publishDate;

    private boolean inGroup;
    private SubmissionOption submissionOption;

    private CourseworkWeight weight;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime lateTurnIn;
    private LocalDateTime closeSubmissionDate;

    private String attachmentUrl;
}
