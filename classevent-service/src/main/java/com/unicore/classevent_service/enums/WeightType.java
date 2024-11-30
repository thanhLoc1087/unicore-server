package com.unicore.classevent_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WeightType {
    MIDTERM, FINAL_TERM;

    @JsonCreator
    public static WeightType fromCode(String code) {
        switch (code) {
            case "MIDTERM":
                return MIDTERM;
            case "FINAL_TERM":
                return FINAL_TERM;
            default:
                return MIDTERM;
        }
    }
}
