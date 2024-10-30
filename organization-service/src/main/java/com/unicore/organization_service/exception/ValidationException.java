package com.unicore.organization_service.exception;

import lombok.Data;
import org.springframework.http.HttpStatusCode;
import java.util.Map;

@Data
public class ValidationException extends RuntimeException{
    private final String code;
    private final Map<String,String> messageMap;
    private final HttpStatusCode status;
    public ValidationException(String code,Map<String,String> message,HttpStatusCode status){
        super();
        this.code = code;
        this.messageMap = message;
        this.status = status;
    }
}