package com.unicore.classevent_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.dto.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionResponse extends BaseDTO {
    private String id;

    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("submitter_code")
    private String submitterCode;
    private boolean group;
    
    private Float grade;
    private String feedback;

    @JsonProperty("reviewer_id")
    private String reviewerId;
    
    @JsonProperty("attachment_id")
    private String attachmentId;
    @JsonProperty("attachment_name")
    private String attachmentName;
    @JsonProperty("attachment_url")
    private String attachmentUrl;
}

