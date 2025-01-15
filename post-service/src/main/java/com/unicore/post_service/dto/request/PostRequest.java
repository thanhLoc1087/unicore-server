package com.unicore.post_service.dto.request;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.post_service.enums.PostType;

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

    @JsonProperty("create_by")
    private String createdBy;

    @JsonProperty("create_email")
    private String creatorEmail;
    
    private PostType type;
    
    @JsonProperty("category_ids")
    private List<String> categoryIds;
}
