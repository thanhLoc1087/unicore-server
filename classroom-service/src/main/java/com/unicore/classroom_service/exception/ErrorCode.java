package com.unicore.classroom_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED("UNK", "Uncategorized error.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST("INV01", "Invalid request.", HttpStatus.BAD_REQUEST),
    NOT_FOUND("NF1001", "Object does not exist.", HttpStatus.NOT_FOUND),
    DUPLICATE("BR1001", "Object already exists.", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED("UA1005", "Unathenticated.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("FB1007", "You shall not proceed since You do not have access.", HttpStatus.FORBIDDEN),
    ;

    ErrorCode(String code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }

    private String code;
    private HttpStatusCode statusCode;
    private String message;
}
