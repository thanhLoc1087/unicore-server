package com.unicore.classroom_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.entity.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroupingAddGroupRequest {
    @JsonProperty("class_id")
    String classId;
    @JsonProperty("subclass_code")
    String subclassCode;
    Group group;
}
