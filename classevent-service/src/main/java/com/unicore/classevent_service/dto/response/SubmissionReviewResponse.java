package com.unicore.classevent_service.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionReviewResponse {
    private String id;

    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("event_name")
    private String eventName;
    @JsonProperty("submission_id")
    private String submissionId;
    @JsonProperty("submitter_id")
    private String submitterId;
    @JsonProperty("submitter_name")
    private String submitterName;

    private int attempt;

    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;

    private Float grade;
    private String feedback;

    @JsonProperty("feedback_date")
    private Date feedbackDate;
    @JsonProperty("reviewer_id")
    private String reviewerId;
    
    @JsonProperty("created_date")
    private Date createdDate;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("modified_date")
    private Date modifiedDate;
    @JsonProperty("modified_by")
    private String modifiedBy;
}
