package com.unicore.classevent_service.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classevent_service.dto.request.ClassGroupingScheduleRequest;
import com.unicore.classevent_service.dto.request.GroupingScheduleRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.GroupingResponse;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.service.GroupingService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grouping")
public class GroupingController {
    private final GroupingService service;

    @GetMapping("/{groupingId}")
    public Mono<ApiResponse<GroupingResponse>> getGroupingById(@PathVariable String groupingId) {
        return service.getGroupingById(groupingId)
            .map(grouping -> new ApiResponse<>(
                grouping, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    

    @PostMapping("/class")
    public Mono<ApiResponse<GroupingResponse>> createClassGrouping(
        @RequestBody ClassGroupingScheduleRequest request
    ) {
        return service.createClassGroupingSchedule(request)
            .map(grouping -> new ApiResponse<>(
                grouping, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PostMapping("/event/{eventId}")
    public Mono<ApiResponse<GroupingResponse>> createEventGrouping(
        @PathVariable String eventId, 
        @RequestBody GroupingScheduleRequest request
    ) {
        return service.createEventGroupingSchedule(eventId, request)
            .map(grouping -> new ApiResponse<>(
                grouping, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PutMapping("/{groupingId}")
    public Mono<ApiResponse<GroupingResponse>> updateGrouping(
        @PathVariable String groupingId, 
        @RequestBody GroupingScheduleRequest request
    ) {
        return service.updateGroupingSchedule(groupingId, request)
            .map(grouping -> new ApiResponse<>(
                grouping, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
}
