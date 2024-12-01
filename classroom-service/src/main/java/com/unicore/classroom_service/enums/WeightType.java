package com.unicore.classroom_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WeightType {
    PRACTICAL, MIDTERM, FINAL_TERM;

    @JsonCreator
    public static WeightType fromCode(String code) {
        switch (code) {
            case "PRACTICAL":
                return PRACTICAL;
            case "MIDTERM":
                return MIDTERM;
            case "FINAL_TERM":
                return FINAL_TERM;
            default:
                return MIDTERM;
        }
    }
    
}
