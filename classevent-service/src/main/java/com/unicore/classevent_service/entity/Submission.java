package com.unicore.classevent_service.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Submission {
    @Id
    private String id;
    
    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;

    
    private String eventId;
    private String submitterId;
    private boolean group;
    
    private Float grade;
    private String feedback;
    private Date feedbackDate;
    private String reviewerId;

    private String attachmentId;
    private String attachmentName;
    private String attachmentUrl;

}
