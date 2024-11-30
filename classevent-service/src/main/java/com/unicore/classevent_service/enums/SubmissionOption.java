package com.unicore.classevent_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SubmissionOption {
    FILE, DRIVE, NONE;
    
    @JsonCreator
    public static SubmissionOption fromCode(String code) {
        switch (code) {
            case "FILE":
                return FILE;
            case "DRIVE":
                return DRIVE;
            case "NONE":
                return NONE;
            default:
                return NONE;
        }
    }
}
