package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionCreationRequest {
    @JsonProperty("event_id")
    private String eventId;
    
    @JsonProperty("student_code")
    private String studentCode;
    @JsonProperty("student_mail")
    private String studentMail;

    @JsonProperty("submission_link")
    private String submissionLink;
}
