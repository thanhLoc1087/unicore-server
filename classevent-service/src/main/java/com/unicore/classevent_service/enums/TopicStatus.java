package com.unicore.classevent_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TopicStatus {
    PENDING, PROCESSING, APPROVED, TURNED_DOWN, TEACHER_ASSIGNED;

    
    @JsonCreator
    public static TopicStatus fromCode(String code) {
        switch (code) {
            case "PENDING":
                return PENDING;
            case "PROCESSING":
                return PROCESSING;
            case "APPROVED":
                return APPROVED;
            case "TURNED_DOWN":
                return TURNED_DOWN;
            case "TEACHER_ASSIGNED":
                return TEACHER_ASSIGNED;
            default:
                return PENDING;
        }
    }
}
