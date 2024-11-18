package com.unicore.classevent_service.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetByDateRequest;
import com.unicore.classevent_service.dto.request.ProjectAddTopicRequest;
import com.unicore.classevent_service.dto.request.ProjectChooseTopicRequest;
import com.unicore.classevent_service.dto.request.ProjectCreationRequest;
import com.unicore.classevent_service.dto.request.ProjectUpdateRequest;
import com.unicore.classevent_service.dto.request.TopicSuggestionRequest;
import com.unicore.classevent_service.dto.response.ProjectResponse;
import com.unicore.classevent_service.entity.Project;
import com.unicore.classevent_service.entity.Topic;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.exception.InvalidRequestException;
import com.unicore.classevent_service.mapper.ProjectMapper;
import com.unicore.classevent_service.repository.ProjectRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public Flux<ProjectResponse> createProject(ProjectCreationRequest request) {
        return Flux.fromIterable(request.getSubclassCodes())
            .map(subclassCode -> {
                Project report = projectMapper.toProject(request);
                report.setSubclassCode(subclassCode);
                report.setCreatedBy("Loc");
                report.setCreatedDate(Date.from(Instant.now()));
                return report;
            })
            .flatMap(this::saveProject);
    }

    private Mono<ProjectResponse> saveProject(Project report) {
        return Mono.just(report)
            .map(entity -> {
                entity.setCreatedBy("Loc Update");
                entity.setCreatedDate(Date.from(Instant.now()));
                
                List<Topic> topics = entity.getTopics();
                for (int i = 0; i < topics.size(); i++) {
                    topics.get(i).setId("" + (1 + i));
                }
                return entity;
            })
            .flatMap(projectRepository::save)
            .map(projectMapper::toProjectResponse);
    }

    /// Các hàm get bên dưới cần gọi qua bên submission xem hoàn thành chưa

    public Mono<ProjectResponse> getReport(String id) {
        return projectRepository.findById(id)
            .map(projectMapper::toProjectResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<ProjectResponse> getClassProjects(GetByClassRequest request) {
        return projectRepository.findByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(projectMapper::toProjectResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<ProjectResponse> getActiveProjects(@Valid GetByDateRequest request) {
        LocalDateTime startDateTime = Optional.ofNullable(request.getDate())
                                            .orElse(LocalDate.now().atStartOfDay());

        // Perform the query and map results
        return projectRepository.findActiveProjects(
                    request.getClassId(),
                    request.getSubclassCode(),
                    startDateTime)
                .map(projectMapper::toProjectResponse)
                .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<ProjectResponse> updateProject(String id, ProjectUpdateRequest request) {
        return projectRepository.findById(id)
            .map(project -> projectMapper.toProject(project, request))
            .flatMap(this::saveProject)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
    
    /// THÊM DANH SÁCH ĐỀ TÀI
    public Mono<ProjectResponse> addTopics(String projectId, ProjectAddTopicRequest request) {
        return projectRepository.findById(projectId)    
            .map(project -> {
                project.setTopics(request.getTopics());
                return project;
            })
            .flatMap(projectRepository::save)
            .map(projectMapper::toProjectResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    /// ĐỀ XUẤT ĐỀ TÀI
    public Mono<ProjectResponse> suggestTopic(String projectId, TopicSuggestionRequest request) {
        return projectRepository.findById(projectId)    
            .map(project -> {
                project.getTopics().add(projectMapper.toTopic(request));
                return project;
            })
            .flatMap(projectRepository::save)
            .map(projectMapper::toProjectResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
    
    /// ĐĂNG KÝ ĐỀ TÀI
    public Mono<ProjectResponse> registerTopic(String projectId, ProjectChooseTopicRequest request) {
        return projectRepository.findById(projectId)
            .flatMap(project -> {
                Topic matchedOption = project.getTopics().stream()
                    .filter(topic -> topic.getId().equals(request.getTopicId()))
                    .findFirst()
                    .orElse(null);

                if (matchedOption != null) {
                    if (matchedOption.getSelectors() == null) {
                        matchedOption.setSelectors(new ArrayList<>());
                    }
                    if (Boolean.TRUE.equals(request.getAdding())) {
                        if (matchedOption.getSelectors().size() < matchedOption.getLimit()) {
                            matchedOption.getSelectors().add(request.getSelector());
                        } else {
                            return Mono.error(new InvalidRequestException());
                        }
                    } else {
                        matchedOption.getSelectors().stream()
                            .filter(selector -> !selector.equals(request.getSelector()));
                    }
                    return projectRepository.save(project);
                } else {
                    return Mono.error(new DataNotFoundException());
                }
            })
            .map(projectMapper::toProjectResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
}
