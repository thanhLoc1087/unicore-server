package com.unicore.profile_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum MemberGender {
    MALE,
    FEMALE;
    
    @JsonCreator
    public static MemberGender fromObject(Object gender) {
        if (gender instanceof String genderStr) {
            if (genderStr.trim().equalsIgnoreCase("male") || 
                genderStr.trim().equalsIgnoreCase("nam")) {
                return MALE;
            } else {
                return FEMALE;
            }
        } else if (gender instanceof Boolean genderBool) {
            return Boolean.TRUE.equals(genderBool) ? MALE : FEMALE;
        }
        throw new IllegalArgumentException("Invalid value for MemberGender: " + gender);
    }
}
