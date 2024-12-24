package com.unicore.post_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public class Bookmark {
    @Id
    private String id;
    @JsonProperty("post_id")
    private String postId;
    @JsonProperty("user_email")
    private String userEmail;
}