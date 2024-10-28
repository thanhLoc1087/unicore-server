package com.unicore.organization_service.entity;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table
public class Staff extends Member {
    private String position;
}
