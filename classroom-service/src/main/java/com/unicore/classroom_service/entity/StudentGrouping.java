package com.unicore.classroom_service.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class StudentGrouping {
    @Id
    private String id;

    @JsonProperty("class_id")
    private String classId;
    
    @JsonProperty("subclass_code")
    private String subclassCode;

    @JsonProperty("start_register_date")
    private LocalDate startRegisterDate;

    @JsonProperty("end_register_date")
    private LocalDate endRegisterDate;

    @JsonProperty("has_leader")
    private boolean hasLeader;

    @JsonProperty("max_size")
    private int maxSize;

    @JsonProperty("min_size")
    private int minSize;

    private List<Group> groups;
}
