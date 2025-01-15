package com.unicore.classevent_service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.Topic;
import com.unicore.classevent_service.enums.WeightType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectUpdateRequest {
    private String name;
    private String description;

    @JsonProperty("allow_grade_review")
    private Boolean allowGradeReview;
    @JsonProperty("review_times")
    private Integer reviewTimes;
    
    private Float weight;

    @JsonProperty("weight_type")
    private WeightType weightType;

    @JsonProperty("in_group")
    private Boolean inGroup;
    
    @JsonProperty("allow_topic_suggestion")
    private Boolean allowTopicSuggestion;
    @JsonProperty("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    private List<Topic> topics;
}
