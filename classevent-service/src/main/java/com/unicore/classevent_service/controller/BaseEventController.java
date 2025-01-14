package com.unicore.classevent_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.unicore.classevent_service.dto.request.GetEventCreationVariablesRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.CreateEventVariables;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.service.BaseEventService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class BaseEventController {
    private final BaseEventService baseEventService;

    @PostMapping("/creation-variables")
    public Mono<ApiResponse<CreateEventVariables>> getCreateEventVariables(@RequestBody GetEventCreationVariablesRequest request) {
        return baseEventService.getCreateEventVariables(request)
            .map(result -> new ApiResponse<>(
                result, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
}
