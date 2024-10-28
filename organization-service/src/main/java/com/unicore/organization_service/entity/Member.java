package com.unicore.organization_service.entity;


import jakarta.persistence.Id;

import java.time.LocalDate;

import com.unicore.organization_service.enums.MemberGender;
import com.unicore.organization_service.enums.MemberRole;

import lombok.Data;

@Data
public abstract class Member {
    @Id
    protected String id;

    protected String organizationId;

    protected String code;
    protected String name;
    protected String email;
    protected LocalDate dob;
    protected MemberGender gender;
    protected MemberRole role;
}
