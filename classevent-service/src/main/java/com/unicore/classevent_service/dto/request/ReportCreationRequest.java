package com.unicore.classevent_service.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
public class ReportCreationRequest {
    @JsonProperty("project_id")
    private String projectId;
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_codes")
    private List<String> subclassCodes;
    private String name;
    private String description;
    private String room;
    @JsonProperty("allow_grade_review")
    private Boolean allowGradeReview;
    @JsonProperty("review_times")
    private Integer reviewTimes;
    
    @JsonProperty("publish_date")
    private LocalDateTime publishDate;
    @JsonProperty("in_group")
    private Boolean inGroup;
    @JsonProperty("submission_option")
    private SubmissionOption submissionOption;
    
    private Float weight;
    @JsonProperty("weight_type")
    private WeightType weightType;
    
    private LocalDate date;
    
    @JsonProperty("remind_grading_date")
    private LocalDate remindGradingDate;
    @JsonProperty("close_submission_date")
    private LocalDate closeSubmissionDate;
    
    @JsonProperty("attachment_url")
    private String attachmentUrl;
    
    private Query query;
}
