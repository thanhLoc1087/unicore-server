package com.unicore.classroom_service.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Classroom {
    @Id
    private String id;

    private String organizationId;

    @Indexed(unique = true)
    private String code;

    private String subjectCode;

    private boolean orgManaged;

    private List<Subclass> subclasses;
    
    private int semester;
    private int year;

    private SubjectMetadata subjectMetadata;
}
