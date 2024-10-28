package com.unicore.organization_service.entity;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table
public class Student extends Member {
    private String advisoryClass;
    private String academicBatch;
}
