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
import com.unicore.classevent_service.dto.request.ReportCreationRequest;
import com.unicore.classevent_service.dto.request.ReportUpdateRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.ReportResponse;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.service.ReportService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    
    @GetMapping
    public Mono<ApiResponse<List<ReportResponse>>> findActiveReports(@RequestBody GetByDateRequest request) {
        return reportService.findActiveReports(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PostMapping
    public Mono<ApiResponse<List<ReportResponse>>> createReport(@RequestBody ReportCreationRequest request) {
        return reportService.createReport(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @GetMapping("/{id}")
    public Mono<ApiResponse<ReportResponse>> getReportById(@PathVariable String id) {
        return reportService.getReport(id)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @GetMapping("/class")
    public Mono<ApiResponse<List<ReportResponse>>> getClassReports(@RequestBody GetByClassRequest request) {
        return reportService.getClassReports(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @GetMapping("/project/{projectId}")
    public Mono<ApiResponse<List<ReportResponse>>> getProjectReports(@PathVariable String projectId) {
        return reportService.getProjectReports(projectId)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PutMapping("/{id}")
    public Mono<ApiResponse<ReportResponse>> updateReport(@PathVariable String id, @RequestBody ReportUpdateRequest request) {
        return reportService.updateReport(id, request)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
}
