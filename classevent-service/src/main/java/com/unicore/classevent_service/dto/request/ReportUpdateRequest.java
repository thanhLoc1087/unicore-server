package com.unicore.classevent_service.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.Query;
import com.unicore.classevent_service.enums.SubmissionOption;
import com.unicore.classevent_service.enums.WeightType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportUpdateRequest {
    private String name;
    private String description;
    private String room;
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
    
    private Float weight;
    @JsonProperty("weight_type")
    private WeightType weightType;
    
    private LocalDate date;
    
    @JsonProperty("late_turn_in")
    private LocalDate lateTurnIn;
    @JsonProperty("close_submission_date")
    private LocalDate closeSubmissionDate;
    
    @JsonProperty("attachment_url")
    private String attachmentUrl;

    private Query query;
}
