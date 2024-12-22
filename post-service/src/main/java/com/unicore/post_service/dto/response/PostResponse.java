package com.unicore.post_service.dto.response;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String content;

    @JsonProperty("created_date")
    private Instant createdDate;

    @JsonProperty("modified_date")
    private Instant modifiedDate;
    
    @JsonProperty("create_by")
    private String createdBy;

    @JsonProperty("create_email")
    private String creatorEmail;

    @JsonProperty("modified_by")
    private String modifiedBy;

    @JsonIgnoreProperties("duplicated")
    private List<CategoryResponse> categories;
}
