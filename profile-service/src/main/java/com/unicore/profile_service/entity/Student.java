package com.unicore.profile_service.entity;

import org.springframework.data.relational.core.mapping.Table;

import com.unicore.profile_service.enums.MemberRole;

import lombok.Data;

@Data
@Table
public class Student extends Member {
    {
        setRole(MemberRole.STUDENT);
    }
    private String advisoryClass;
    private String academicBatch;
}
