package com.unicore.post_service.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.post_service.entity.Editor;
import com.unicore.post_service.enums.PostType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    private String id;
    private String name;
    private String description;

    @JsonProperty("source_id")
    private String sourceId;

    @JsonProperty("created_date")
    private String createdDate;
    
    @JsonProperty("create_by")
    private String createdBy;

    @JsonProperty("create_email")
    private String creatorEmail;

    @JsonProperty("modified_by")
    private List<Editor> modifiedBy;

    private PostType type;

    @JsonIgnoreProperties("duplicated")
    private List<CategoryResponse> categories;
}
