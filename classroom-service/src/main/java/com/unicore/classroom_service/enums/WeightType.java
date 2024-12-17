package com.unicore.classroom_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WeightType {
    COURSEWORK, PRACTICAL, MIDTERM, FINAL_TERM;

    @JsonCreator
    public static WeightType fromCode(String code) {
        switch (code) {
            case "COURSEWORK", "QT":
                return COURSEWORK;
            case "PRACTICAL", "TH":
                return PRACTICAL;
            case "MIDTERM", "GK":
                return MIDTERM;
            case "FINAL_TERM", "CK":
                return FINAL_TERM;
            default:
                return MIDTERM;
        }
    }
    
}
