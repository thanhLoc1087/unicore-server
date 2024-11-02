package com.unicore.profile_service.entity;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table
public class Staff extends Member {
    private String position;
}
