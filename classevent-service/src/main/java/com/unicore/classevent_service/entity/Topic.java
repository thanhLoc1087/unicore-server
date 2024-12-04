package com.unicore.classevent_service.entity;

import java.util.List;
import java.util.UUID;

import com.unicore.classevent_service.enums.TopicStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic {
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private String description;
    private String teacherCode;
    private String teacherName;
    private int limit;

    @Builder.Default
    private boolean official = true;

    @Builder.Default
    private TopicStatus status = TopicStatus.APPROVED;

    private String feedback;

    // group indices or student codes
    private List<String> selectors;
}
