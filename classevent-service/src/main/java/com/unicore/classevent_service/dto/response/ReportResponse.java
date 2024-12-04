package com.unicore.classevent_service.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.SubmissionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ReportResponse extends BaseEventResponse{
    private String place;

    @JsonProperty("project_id")
    private String projectId;
    
    @JsonProperty("publish_date")
    private LocalDateTime publishDate;
    @JsonProperty("submission_option")
    private SubmissionOption submissionOption;
    
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

    @Override
    public EventType getEventType() {
        return EventType.REPORT;
    }
}
