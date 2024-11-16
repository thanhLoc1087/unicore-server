package com.unicore.classevent_service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.CourseworkWeight;
import com.unicore.classevent_service.enums.SubmissionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportCreationRequest {
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_codes")
    private List<String> subclassCodes;
    private String name;
    private String description;
    private String place;
    @JsonProperty("allow_grade_review")
    private boolean allowGradeReview;
    @JsonProperty("review_times")
    private int reviewTimes;
    // người chấm
    @JsonProperty("grader_code")
    private String graderCode;
    
    @JsonProperty("publish_date")
    private LocalDateTime publishDate;
    @JsonProperty("in_group")
    private boolean inGroup;
    @JsonProperty("submission_option")
    private SubmissionOption submissionOption;
    
    private CourseworkWeight weight;
    
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonProperty("end_date")
    private LocalDateTime endDate;
    
    @JsonProperty("remind_grading_date")
    private LocalDateTime remindGradingDate;
    @JsonProperty("close_submission_date")
    private LocalDateTime closeSubmissionDate;
    
    @JsonProperty("attachment_url")
    private String attachmentUrl;
    
}
