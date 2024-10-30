package com.unicore.organization_service.exception;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.unicore.organization_service.dto.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<String>> handlingRuntimeException(Exception exception) {
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED;
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(errorCode.getCode());
        response.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<String>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }

    // @ExceptionHandler(value = AccessDeniedException.class)
    // public ResponseEntity<ApiResponse<String>> handlingAccessDeniedException(AccessDeniedException exception) {
    //     ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
    //     return ResponseEntity.status(errorCode.getStatusCode())
    //             .body(ApiResponse.<String>builder()
    //                     .code(errorCode.getCode())
    //                     .message(errorCode.getMessage())
    //                     .build());
    // }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleCommonException(ValidationException ex) {
        return ResponseEntity
            .status(ex.getStatus())
            .body(ex.getMessageMap());
    }
}
