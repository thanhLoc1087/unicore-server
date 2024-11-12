package com.unicore.classroom_service.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
    private String index;
    private String name;
    @JsonProperty("teacher_code")
    private String teacherCode;
    private List<StudentInGroup> members;
}
