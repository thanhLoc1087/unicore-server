package com.unicore.organization_service.entity;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table
public class Teacher extends Member {
    private String degree;
    private String researchDirection;
}
