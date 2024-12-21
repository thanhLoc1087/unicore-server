package com.unicore.grade_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Ranking {
    EXCELLENT("Xuất sắc"), 
    GOOD("Tốt"), 
    MID("Khá"),
    AVERAGE("Trung bình"), 
    BAD("Kém");
    
    private String name;

    @JsonValue
    public String getName() {
        return name;
    }
    
    @JsonCreator
    public static Ranking fromString(String text) {
        for (Ranking type : Ranking.values()) {
            if (type.name.equals(text)) {
                return type;
            }
        }
        return MID;
    } 
}
