package com.unicore.organization_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum MemberGender {
    MALE,
    FEMALE;
    
    @JsonCreator
    public static MemberGender fromCode(boolean isMale) {
        return isMale ? MALE : FEMALE;
    }
}
