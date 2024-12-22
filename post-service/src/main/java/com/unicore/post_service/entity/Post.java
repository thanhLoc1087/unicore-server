package com.unicore.post_service.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(value = "post")
public class Post {
    @MongoId
    private String id;
    private String content;
    private Instant createdDate;
    private Instant modifiedDate;
    private String createdBy;
    private String creatorEmail;
    private String modifiedBy;

    private List<String> categoryIds;
}