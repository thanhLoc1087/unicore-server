package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Project {
    @Id
    private String id;
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;
    
    private String classId;
    private String subclassCode;
    private String name;
    private String description;
    private LocalDateTime startDate;

    private Float weight;
    private WeightType weightType;
    
    private boolean allowGradeReview;
    private int reviewTimes;

    private boolean allowTopicSuggestion;

    private List<Topic> topics;
}
