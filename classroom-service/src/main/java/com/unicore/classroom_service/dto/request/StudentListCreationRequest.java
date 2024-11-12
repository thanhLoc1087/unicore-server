package com.unicore.classroom_service.dto.request;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.entity.StudentInGroup;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentListCreationRequest {
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
    private List<StudentInGroup> foreignStudents;
}
