package com.unicore.classroom_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ClassType {
    LY_THUYET, THUC_HANH;
    
    @JsonCreator
    public static ClassType fromCode(String code) {
        return code.equals("TH") ? THUC_HANH : LY_THUYET;
    }
}
