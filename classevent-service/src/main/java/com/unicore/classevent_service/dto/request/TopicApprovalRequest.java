package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicApprovalRequest {
    @JsonProperty("topic_id")
    private String topicId;
    private String feedback;

    private boolean approved = false;
}
