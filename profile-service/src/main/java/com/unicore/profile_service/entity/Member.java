package com.unicore.profile_service.entity;


import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import com.unicore.profile_service.enums.MemberGender;
import com.unicore.profile_service.enums.MemberRole;

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
