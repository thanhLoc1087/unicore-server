package com.unicore.classevent_service.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
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

    @JsonProperty("in_group")
    private boolean inGroup;

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
}
