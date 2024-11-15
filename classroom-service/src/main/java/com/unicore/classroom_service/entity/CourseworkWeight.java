package com.unicore.classroom_service.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public class CourseworkWeight {
    @Id
    private String id;
    private String classId;
    private String subclassCode;
    
    private List<CourseworkWeightDetail> details;
}

