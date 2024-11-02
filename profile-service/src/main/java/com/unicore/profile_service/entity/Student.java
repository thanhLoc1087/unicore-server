package com.unicore.profile_service.entity;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table
public class Student extends Member {
    private String advisoryClass;
    private String academicBatch;
}
