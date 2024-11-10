package com.unicore.classroom_service.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.entity.Group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentGroupingCreationRequest {
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
    
    @JsonProperty("create_subclass")
    private boolean createSubclass;

    private List<Group> groups;
}
