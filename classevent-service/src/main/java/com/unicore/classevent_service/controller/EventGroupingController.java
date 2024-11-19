package com.unicore.classevent_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classevent_service.dto.request.EventGroupingAddGroupRequest;
import com.unicore.classevent_service.dto.request.EventGroupingCreationRequest;
import com.unicore.classevent_service.dto.request.EventGroupingUpdateRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.EventGroupingResponse;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.service.EventGroupingService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequiredArgsConstructor
@RequestMapping("/groupings")
public class EventGroupingController {
    private final EventGroupingService service;

    @GetMapping("/{eventId}")
    public Mono<ApiResponse<EventGroupingResponse>> getEventGrouping(@PathVariable String eventId) {
        return service.getGrouping(eventId)
            .map(grouping -> new ApiResponse<>(
                grouping, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now())
            );
    }
    
    @PostMapping("/homework")
    public Mono<ApiResponse<EventGroupingResponse>> createHomeworkGrouping (@RequestBody EventGroupingCreationRequest request) {
        return service.createEventGrouping(request, EventType.HOMEWORK)
            .map(grouping -> new ApiResponse<>(
                    grouping, 
                    ApiMessage.SUCCESS.getMessage(), 
                    HttpStatus.OK.value(),
                    LocalDateTime.now())
                );
    }
    
    @PostMapping("/project")
    public Mono<ApiResponse<EventGroupingResponse>> createProjectGrouping (@RequestBody EventGroupingCreationRequest request) {
        return service.createEventGrouping(request, EventType.PROJECT)
            .map(grouping -> new ApiResponse<>(
                    grouping, 
                    ApiMessage.SUCCESS.getMessage(), 
                    HttpStatus.OK.value(),
                    LocalDateTime.now())
                );
    }
    
    @PutMapping("/{id}")
    public Mono<ApiResponse<EventGroupingResponse>> putMethodName(@PathVariable String id, @RequestBody EventGroupingUpdateRequest request) {
        return service.updateEventGrouping(id, request)
            .map(grouping -> new ApiResponse<>(
                grouping, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now())
            );
    }

    @PostMapping
    public Mono<ApiResponse<EventGroupingResponse>> addGroup(@RequestBody EventGroupingAddGroupRequest request) {        
        return service.addGroupToEventGrouping(request)
            .map(grouping -> new ApiResponse<>(
                grouping, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now())
            );
    }
    
}
