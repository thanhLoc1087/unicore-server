package com.unicore.classevent_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TopicStatus {
    PENDING, PROCESSING, APPROVED;

    
    @JsonCreator
    public static TopicStatus fromCode(String code) {
        switch (code) {
            case "PENDING":
                return PENDING;
            case "PROCESSING":
                return PROCESSING;
            case "APPROVED":
                return APPROVED;
            default:
                return PENDING;
        }
    }
}
