package com.unicore.classevent_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClassType {
    LOP_THUONG("NOR", true), 
    LY_THUYET("LT", true), 
    HINH_THUC_1("HT1", false),
    HINH_THUC_2("HT2", false),
    DO_AN("DA", true),
    THUC_TAP("TTTN", true),
    KHOA_LUAN("KLTN", true),
    NHOM_HUONG_DAN("NHD", false)
    ;

    private String code;
    private boolean mainClass;

    @JsonValue
    public String getCode() {
        return code;
    }
    
    @JsonCreator
    public static ClassType fromCode(String code) {
        for (ClassType type : ClassType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return LY_THUYET;
    }
}
