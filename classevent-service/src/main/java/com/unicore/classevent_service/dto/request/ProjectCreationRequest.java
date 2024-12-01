package com.unicore.classevent_service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.Topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectCreationRequest {
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;

    private String name;
    private String description;
    private Float weight;

    @JsonProperty("allow_grade_review")
    private boolean allowGradeReview;
    @JsonProperty("review_times")
    private int reviewTimes;    
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    
    @JsonProperty("allow_topic_suggestion")
    private boolean allowTopicSuggestion;

    private List<Topic> topics;
}
