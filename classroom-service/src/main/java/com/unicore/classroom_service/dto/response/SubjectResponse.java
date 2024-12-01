package com.unicore.classroom_service.dto.response;

import com.unicore.classroom_service.entity.SubjectMetadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
    private String id;
    private String name;
    private String code;
    private String description;
    private SubjectMetadata metadata;
}
