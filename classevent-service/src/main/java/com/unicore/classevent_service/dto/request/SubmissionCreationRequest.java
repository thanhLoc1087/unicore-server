package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.EventType;

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

    @JsonProperty("submitter_code")
    private String submitterCode;

    private EventType eventType;
    
    @JsonProperty("attachment_id")
    private String attachmentId;
    @JsonProperty("attachment_name")
    private String attachmentName;
    @JsonProperty("attachment_url")
    private String attachmentUrl;
}
