package com.unicore.classroom_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classroom_service.dto.request.CourseworkWeightCreationRequest;
import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.response.CourseworkWeightResponse;
import com.unicore.classroom_service.service.CourseworkWeightService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
public class CourseworkWeightController {
    private final CourseworkWeightService courseworkWeightService;

    @GetMapping
    public Mono<CourseworkWeightResponse> getMethodName(@RequestBody GetByClassRequest request) {
        return courseworkWeightService.getCourseworkWeightByClass(request);
    }
    
    @PostMapping
    public Mono<CourseworkWeightResponse> create(@RequestBody CourseworkWeightCreationRequest request) {
        return courseworkWeightService.createCourseworkWeight(request);
    }


    @GetMapping("/{id}")
    public Mono<CourseworkWeightResponse> getById (@PathVariable String id) {
        return courseworkWeightService.getCourseworkWeightById(id);
    }
    
}
