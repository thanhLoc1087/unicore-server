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
import com.unicore.classevent_service.dto.request.ProjectAddTopicRequest;
import com.unicore.classevent_service.dto.request.ProjectChooseTopicRequest;
import com.unicore.classevent_service.dto.request.ProjectCreationRequest;
import com.unicore.classevent_service.dto.request.ProjectTopicRequest;
import com.unicore.classevent_service.dto.request.ProjectUpdateRequest;
import com.unicore.classevent_service.dto.request.TopicApprovalRequest;
import com.unicore.classevent_service.dto.request.TopicRegisterScheduleRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.ProjectResponse;
import com.unicore.classevent_service.entity.ProjectTopic;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/active")
    public Mono<ApiResponse<List<ProjectResponse>>> getActiveProjects(@RequestBody GetByDateRequest request) {
        return projectService.getActiveProjects(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PostMapping("/class")
    public Mono<ApiResponse<List<ProjectResponse>>> getClassProjects(@RequestBody GetByClassRequest request) {
        return projectService.getClassProjects(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @GetMapping("/{id}")
    public Mono<ApiResponse<ProjectResponse>> getProjectById(@PathVariable String id) {
        return projectService.getProject(id)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PostMapping
    public Mono<ApiResponse<ProjectResponse>> createProject(@RequestBody ProjectCreationRequest request) {
        return projectService.createProject(request)
            .map(project -> new ApiResponse<>(
                project, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PutMapping("/{id}")
    public Mono<ApiResponse<ProjectResponse>> updateProject(@PathVariable String id, @RequestBody ProjectUpdateRequest request) {
        return projectService.updateProject(id, request)
            .map(project -> new ApiResponse<>(
                project, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PostMapping("/{id}/topics/schedule")
    public Mono<ApiResponse<ProjectResponse>> createRegisterTopicSchedule(@PathVariable String id, @RequestBody TopicRegisterScheduleRequest request) {
        return projectService.createRegisterTopicSchedule(id, request)
            .map(project -> new ApiResponse<>(
                project, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    

    @PutMapping("/{id}/topics/import")
    public Mono<ApiResponse<List<ProjectTopic>>> addTopics(@PathVariable String id, @RequestBody @Valid ProjectAddTopicRequest request) {
        return projectService.addTopics(id, request)
            .collectList()
            .map(project -> new ApiResponse<>(
                project, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PutMapping("/{id}/topics/suggest")
    public Mono<ApiResponse<ProjectTopic>> suggestTopic(@PathVariable String id, @RequestBody @Valid ProjectTopicRequest request) {
        return projectService.suggestTopic(id, request)
            .map(project -> new ApiResponse<>(
                project, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PutMapping("/topics/approve")
    public Mono<ApiResponse<ProjectTopic>> approveTopic(@RequestBody @Valid TopicApprovalRequest request) {
        return projectService.approveTopic(request)
            .map(project -> new ApiResponse<>(
                project, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PutMapping("/topics/register")
    public Mono<ApiResponse<ProjectTopic>> registerTopic(@RequestBody @Valid ProjectChooseTopicRequest request) {
        return projectService.registerTopic(request)
            .map(project -> new ApiResponse<>(
                project, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PostMapping("/topics")
    public Mono<ApiResponse<List<ProjectResponse>>> getProjectsByTopicIds(@RequestBody List<String> topicIds) {
        return projectService.getByTopicIds(topicIds)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @PostMapping("/{projectId}/topics")
    public Mono<ApiResponse<List<ProjectTopic>>> getProjectsByProjectId(@PathVariable String projectId) {
        return projectService.getTopicsByProjectId(projectId)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
}
