package com.unicore.classroom_service.dto.response;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentListResponse {
    private String id;
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;
    @JsonProperty("leader_code")
    private String leaderCode;
    @JsonProperty("student_codes")
    private Set<String> studentCodes;

    @JsonProperty("foreign_students")
    /// studentCode : < classId, subclassCode >
    private List<String> foreignStudentCodes;
}
