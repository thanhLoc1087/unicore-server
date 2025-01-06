package com.unicore.profile_service.entity;

import org.springframework.data.relational.core.mapping.Table;

import com.unicore.profile_service.enums.MemberRole;

import lombok.Data;

@Data
@Table
public class Staff extends Member {
    {
        setRole(MemberRole.STAFF);
    }
    private String position;

    private boolean manager = false;
}
