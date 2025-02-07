package com.unicore.post_service.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(value = "post")
public class Comment {
    @MongoId
    private String id;
    private String sourceId;
    private String content;
    private LocalDateTime createdDate;
    private String creatorName;
    private String creatorEmail;
}
