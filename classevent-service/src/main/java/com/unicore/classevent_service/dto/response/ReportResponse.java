package com.unicore.classevent_service.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.Query;
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
    private String room;

    @JsonProperty("project_id")
    private String projectId;
    
    @JsonProperty("publish_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime publishDate;
    @JsonProperty("submission_options")
    private List<SubmissionOption> submissionOptions;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("report_date")
    private LocalDate reportDate;
    
    @JsonProperty("remind_grading_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime remindGradingDate;
    @JsonProperty("close_submission_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime closeSubmissionDate;

    private boolean autocreated;
    
    @JsonProperty("attachment_url")
    private String attachmentUrl;

    @JsonProperty("comment_count")
    private int commentCount;

    private Query query;

    @Override
    public EventType getEventType() {
        return EventType.REPORT;
    }
}
