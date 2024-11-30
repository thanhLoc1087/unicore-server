package com.unicore.classevent_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classevent_service.dto.request.GeneralTestBulkCreationRequest;
import com.unicore.classevent_service.dto.request.GeneralTestUpdateRequest;
import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.GeneralTestResponse;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.service.GeneralTestService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
public class GeneralTestController {
    private final GeneralTestService service;

    @GetMapping
    public Mono<ApiResponse<List<GeneralTestResponse>>> getClassTests(@RequestBody GetByClassRequest request) {
        return service.getByClass(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<GeneralTestResponse>> getById(@PathVariable String id) {
        return service.getById(id)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PostMapping
    public Mono<ApiResponse<List<GeneralTestResponse>>> createBulk(@RequestBody GeneralTestBulkCreationRequest request) {
        return service.createBulk(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PutMapping("/{id}")
    public Mono<ApiResponse<GeneralTestResponse>> editTest(@PathVariable String id, @RequestBody GeneralTestUpdateRequest request) {
        return service.editTest(id, request)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
}
