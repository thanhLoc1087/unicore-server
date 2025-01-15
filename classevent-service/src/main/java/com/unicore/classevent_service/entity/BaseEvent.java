package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.WeightType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nonapi.io.github.classgraph.json.Id;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseEvent {
    @Id
    private String id;
    @JsonProperty("created_date")
    private Date createdDate;
    @JsonProperty("modified_date")
    private Date modifiedDate;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("modified_by")
    private String modifiedBy;

    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    @JsonProperty("in_group")
    private boolean inGroup;

    @JsonProperty("class_id")
    private String classId;
    
    @JsonProperty("subclass_code")
    private String subclassCode;

    private Float weight;

    @Builder.Default
    @JsonProperty("fixed_weight")
    private boolean fixedWeight = true;

    @JsonProperty("weight_type")
    private WeightType weightType;

    private String name;
    private String description;
    @JsonProperty("allow_grade_review")
    private boolean allowGradeReview;
    @JsonProperty("review_times")
    private int reviewTimes;

    private String groupingId;

    private EventType type;
}
