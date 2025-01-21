package com.unicore.classroom_service.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentList {
    @Id
    private String id;

    @Indexed(unique = true)
    @JsonProperty("class_id")
    private String classId;
    
    @JsonProperty("subclass_code")
    private String subclassCode;
    
    private int semester;
    private int year;
    
    private String leaderCode;
    
    @JsonProperty("student_codes")
    private Set<String> studentCodes;
    
    @JsonProperty("foreign_students")
    @Builder.Default
    private Set<String> foreignStudentCodes = new HashSet<>();
}
