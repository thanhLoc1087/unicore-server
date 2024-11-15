package com.unicore.classevent_service.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.CourseworkWeight;
import com.unicore.classevent_service.enums.SubmissionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeworkUpdateRequest {
    @JsonProperty("modified_by")
    private String modifiedBy;
    @JsonProperty("homework_id")
    private String homeworkId;
    private String name;
    private String description;
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
    
    @JsonProperty("late_turn_in")
    private LocalDateTime lateTurnIn;
    @JsonProperty("close_submission_date")
    private LocalDateTime closeSubmissionDate;
    
    @JsonProperty("attachment_url")
    private String attachmentUrl;
}
