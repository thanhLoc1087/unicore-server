package com.unicore.common_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED("UNK", "Uncategorized error.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST("INV01", "Invalid request.", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS("NF1001", "User does not exist.", HttpStatus.NOT_FOUND),
    USER_EXISTS("BR1002", "User already exists.", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID("BR1003", "Username must have at least {min} characters.", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID("BR1004", "Password must have at least {min} characters.", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED("UA1005", "Unathenticated.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("FB1007", "You shall not proceed since You do not have access.", HttpStatus.FORBIDDEN),
    INVALID_DOB("BR1008", "You must be at least {min} years old to have an account.", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL("BR1009", "Invalid email address", HttpStatus.BAD_REQUEST),
    EMAIL_IS_REQUIRED("BR1010", "Email is required", HttpStatus.BAD_REQUEST),
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
