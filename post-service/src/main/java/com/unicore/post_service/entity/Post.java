package com.unicore.post_service.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.unicore.post_service.enums.PostType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(value = "post")
public class Post {
    @MongoId
    private String id;
    private String sourceId;
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private String createdBy;
    private String creatorEmail;
    @Builder.Default
    private List<Editor> modifiedBy = new ArrayList<>();
    private PostType type;
    @Builder.Default
    private List<String> categoryIds = new ArrayList<>();

    @Builder.Default
    private int commentCount = 0;
}