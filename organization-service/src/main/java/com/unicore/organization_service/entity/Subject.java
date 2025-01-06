package com.unicore.organization_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @Id
    private String id;
    
    private String organizationId;

    private String name;

    private String code;

    private String description;

    private boolean deleted;
}
