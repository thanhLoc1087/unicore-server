package com.unicore.classevent_service.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInClassResponse {
    private List<StudentResponse> students;

    @JsonProperty("foreign_students")
    private List<StudentResponse> foreignStudents;
}
