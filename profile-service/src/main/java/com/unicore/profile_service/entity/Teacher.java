package com.unicore.profile_service.entity;

import org.springframework.data.relational.core.mapping.Table;

import com.unicore.profile_service.enums.MemberRole;

import lombok.Data;

@Data
@Table
public class Teacher extends Member {
    {
        setRole(MemberRole.TEACHER);
    }
    private String degree;
    private String researchDirection;
    private String researchConcern;
}
