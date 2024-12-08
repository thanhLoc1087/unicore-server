package com.unicore.profile_service.entity;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table
public class Teacher extends Member {
    private String degree;
    private String researchDirection;
    private String researchConcern;
}
