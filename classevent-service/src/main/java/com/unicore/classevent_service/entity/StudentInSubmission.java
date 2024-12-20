package com.unicore.classevent_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentInSubmission {
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;
    @JsonProperty("student_code")
    private String studentCode;
    private String name;
    private String phone;
    @JsonProperty("group_name")
    private String groupName;

    private Float grade;

    public static StudentInSubmission fromStudentInGroup(StudentInGroup inGroup) {
        return StudentInSubmission.builder()
            .classId(inGroup.getClassId())
            .subclassCode(inGroup.getSubclassCode())
            .studentCode(inGroup.getStudentCode())
            .name(inGroup.getName())
            .phone(inGroup.getPhone())
            .groupName(inGroup.getGroupName())
            .grade(0f)
            .build();
    }
}

