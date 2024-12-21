package com.unicore.grade_service.enums;

public enum ApiMessage {
    SUCCESS("Success"),
    NOT_FOUND("Resource not found"),
    ERROR("An error occurred");

    private final String message;

    ApiMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
