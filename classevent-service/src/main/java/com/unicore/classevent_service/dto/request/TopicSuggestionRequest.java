package com.unicore.classevent_service.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String nameEn;
    private String description;
    @JsonProperty("teacher_codes")
    private List<String> teacherCodes;
    @JsonProperty("teacher_names")
    private List<String> teacherNames;
    private int limit;

    @Builder.Default
    private boolean official = false;

    // group indices or student codes
    private List<String> selectors;
    
}
