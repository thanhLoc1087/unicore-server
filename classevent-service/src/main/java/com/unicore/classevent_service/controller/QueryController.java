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

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetByDateRequest;
import com.unicore.classevent_service.dto.request.QueryCreationRequest;
import com.unicore.classevent_service.dto.request.QueryUpdateRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.QueryResponse;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.service.QueryService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queries")
public class QueryController {
    private final QueryService queryService;

    @GetMapping
    public Flux<ApiResponse<QueryResponse>> findActiveQueries(@RequestBody GetByDateRequest request) {
        return queryService.findActiveQueries(request)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PostMapping
    public Mono<ApiResponse<QueryResponse>> createQuery(@RequestBody QueryCreationRequest request) {
        return queryService.createQuery(request)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @GetMapping("/{id}")
    public Mono<ApiResponse<QueryResponse>> getQueryById(@PathVariable String id) {
        return queryService.getQuery(id)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @GetMapping("/class")
    public Mono<ApiResponse<List<QueryResponse>>> getClassQueries(@RequestBody GetByClassRequest request) {
        return queryService.getClassQueries(request)
            .collectList()
            .map(queries -> new ApiResponse<>(
                queries, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PutMapping("/{id}")
    public Mono<ApiResponse<QueryResponse>> updateQuery(@PathVariable String id, @RequestBody QueryUpdateRequest request) {
        return queryService.updateQuery(id, request)
            .map(query -> new ApiResponse<>(
                query, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
}
