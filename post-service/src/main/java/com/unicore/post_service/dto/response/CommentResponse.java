package com.unicore.post_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String id;
    @JsonProperty("source_id")
    private String sourceId;
    private String content;
    @JsonProperty("creator_name")
    private String creatorName;
    @JsonProperty("creator_email")
    private String creatorEmail;
 
    @JsonProperty("created_date")
    private String createdDate;   
}
