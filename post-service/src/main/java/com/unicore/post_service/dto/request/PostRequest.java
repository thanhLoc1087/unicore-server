package com.unicore.post_service.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String name;

    private String description;

    @JsonProperty("source_id")
    private String sourceId;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("creator_email")
    private String creatorEmail;
    
    @JsonProperty("category_ids")
    private List<String> categoryIds;
}
