package com.unicore.classevent_service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.dto.BaseDTO;
import com.unicore.classevent_service.entity.StudentInGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionResponse {
    private String id;

    @JsonProperty("created_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    protected LocalDateTime createdDate;
    @JsonProperty("modified_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    protected LocalDateTime modifiedDate;
    @JsonProperty("created_by")
    protected String createdBy;
    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("event_id")
    private String eventId;
    
    private List<StudentInGroup> submitters;
    private boolean group;
    
    private Float grade;
    private String feedback;
    
    @JsonProperty("attachment_id")
    private String attachmentId;
    @JsonProperty("attachment_name")
    private String attachmentName;
    @JsonProperty("attachment_url")
    private String attachmentUrl;

    @JsonProperty("submit_time_status")
    private String submitTimeStatus;
}

