package com.unicore.classroom_service.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String classCode;

    private int semester;
    private int year;

    private String leaderCode;
    private Set<String> studentCodes;
}
