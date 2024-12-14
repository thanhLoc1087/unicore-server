package com.unicore.classevent_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classevent_service.dto.request.CentralizedTestRequest;
import com.unicore.classevent_service.dto.request.GeneralTestBulkCreationRequest;
import com.unicore.classevent_service.dto.request.GeneralTestUpdateRequest;
import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.BaseEventResponse;
import com.unicore.classevent_service.dto.response.GeneralTestResponse;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.service.GeneralTestService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tests")
@Slf4j
public class GeneralTestController {
    @PostConstruct public void init() { log.info("GeneralEventController initialized"); }
    private final GeneralTestService service;
    
    @PostMapping("/class")
    public Mono<ApiResponse<List<GeneralTestResponse>>> getClassTests(@RequestBody GetByClassRequest request) {
        log.info("AYOOOOOOOOOOOOOOOOOOOOOOOOOOOOO {}", request);
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
    
    @PostMapping("/internal")
    public Mono<ApiResponse<List<BaseEventResponse>>> createBulk(@RequestBody GeneralTestBulkCreationRequest request) {
        log.info("ALOOOOOOOOOOOO {}", request);
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

    @PutMapping("/edit-bulk")
    public Mono<ApiResponse<List<GeneralTestResponse>>> editTestsBulk(@RequestBody CentralizedTestRequest request) {
        return service.updateBulk(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
}
