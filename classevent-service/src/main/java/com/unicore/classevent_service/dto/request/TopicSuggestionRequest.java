package com.unicore.classevent_service.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicSuggestionRequest {
    private String name;
    private String description;
    private String teacherCode;
    private String teacherName;
    private int limit;

    @Builder.Default
    private boolean official = false;

    // group indices or student codes
    private List<String> selectors;
    
}
