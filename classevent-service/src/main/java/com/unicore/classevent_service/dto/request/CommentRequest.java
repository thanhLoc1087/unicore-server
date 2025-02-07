package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    @JsonProperty("source_id")
    private String sourceId;
    private String content;
    @JsonProperty("creator_name")
    private String creatorName;
    @JsonProperty("creator_email")
    private String creatorEmail;
}
