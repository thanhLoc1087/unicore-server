package com.unicore.classevent_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetByDateRequest;
import com.unicore.classevent_service.dto.request.HomeworkCreationRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.HomeworkResponse;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.service.HomeworkService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/homework")
@RequiredArgsConstructor
public class HomeworkController {
    private final HomeworkService homeworkService;

    @GetMapping
    public Flux<ApiResponse<HomeworkResponse>> findActiveHomework(@RequestBody GetByDateRequest request) {
        return homeworkService.findActiveHomework(request)
            .map(homework -> new ApiResponse<>(
                homework, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PostMapping
    public Mono<ApiResponse<List<HomeworkResponse>>> createHomework(@RequestBody HomeworkCreationRequest request) {
        return homeworkService.createHomework(request)
            .collectList()
            .map(homework -> new ApiResponse<>(
                homework, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @GetMapping("/{id}")
    public Mono<ApiResponse<HomeworkResponse>> getHomeworkById(@PathVariable String id) {
        return homeworkService.getHomework(id)
            .map(homework -> new ApiResponse<>(
                homework, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @GetMapping("/class")
    public Mono<ApiResponse<List<HomeworkResponse>>> getClassHomework(@RequestBody GetByClassRequest request) {
        return homeworkService.getClassHomework(request)
            .collectList()
            .map(homework -> new ApiResponse<>(
                homework, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
}
