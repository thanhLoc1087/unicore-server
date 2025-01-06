package com.unicore.profile_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum MemberGender {
    MALE,
    FEMALE;
    
    @JsonCreator
    public static MemberGender fromString(String gender) {
        if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("name")) {
            return MALE;
        }
        return FEMALE;
    }
}
