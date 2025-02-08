package com.unicore.classevent_service.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.WeightType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEventResponse {
    private String id;
    @JsonProperty("created_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
    @JsonProperty("modified_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date modifiedDate;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("modified_by")
    private String modifiedBy;
    
    @JsonProperty("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    
    @JsonProperty("end_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @JsonProperty("in_group")
    private boolean inGroup;

    private Float weight;
    @JsonProperty("weight_type")
    private WeightType weightType;

    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;

    private String name;
    private String description;
    @JsonProperty("allow_grade_review")
    private boolean allowGradeReview;
    @JsonProperty("review_times")
    private int reviewTimes;

    @JsonProperty("grouping_id")
    private String groupingId;

    public abstract EventType getEventType();
}
