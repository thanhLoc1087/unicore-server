package com.unicore.classevent_service.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.EventGroupingAddGroupRequest;
import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetByDateRequest;
import com.unicore.classevent_service.dto.request.ProjectAddTopicRequest;
import com.unicore.classevent_service.dto.request.ProjectChooseTopicRequest;
import com.unicore.classevent_service.dto.request.ProjectCreationRequest;
import com.unicore.classevent_service.dto.request.ProjectUpdateRequest;
import com.unicore.classevent_service.dto.request.TopicApprovalRequest;
import com.unicore.classevent_service.dto.request.TopicSuggestionRequest;
import com.unicore.classevent_service.dto.response.ProjectResponse;
import com.unicore.classevent_service.entity.Project;
import com.unicore.classevent_service.entity.Topic;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.TopicStatus;
import com.unicore.classevent_service.enums.WeightType;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.exception.InvalidRequestException;
import com.unicore.classevent_service.mapper.ProjectMapper;
import com.unicore.classevent_service.repository.BaseEventRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final BaseEventRepository projectRepository;
    private final ProjectMapper projectMapper;

    private final EventGroupingService eventGroupingService;


    public Mono<ProjectResponse> createProject(ProjectCreationRequest request) {
        return Mono.just(request)
            .map(subclassCode -> {
                Project project = projectMapper.toProject(request);
                project.setWeightType(WeightType.COURSEWORK);
                project.setCreatedBy("Loc");
                project.setCreatedDate(Date.from(Instant.now()));
                return project;
            })
            .flatMap(this::saveProject);
    }

    public Mono<ProjectResponse> saveProject(Project project) {
        return Mono.just(project)
            .map(entity -> {
                entity.setCreatedBy("Loc Create");
                entity.setCreatedDate(Date.from(Instant.now()));
                
                List<Topic> topics = Objects.requireNonNullElse(entity.getTopics(), new ArrayList<>());
                for (int i = 0; i < topics.size(); i++) {
                    topics.get(i).setId("" + (1 + i));
                }
                return entity;
            })
            .flatMap(projectRepository::save)
            .map(projectMapper::toProjectResponse)
            .doOnError(e -> 
                log.error("Save Project error", e)
            );
    }

    /// Các hàm get bên dưới cần gọi qua bên submission xem hoàn thành chưa

    public Mono<ProjectResponse> getProject(String id) {
        return projectRepository.findById(id)
            .map(project -> projectMapper.toProjectResponse((Project) project))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<ProjectResponse> getClassProjects(GetByClassRequest request) {
        return projectRepository.findAllByClassIdAndSubclassCodeAndType(
                request.getClassId(), 
                request.getSubclassCode(), 
                EventType.PROJECT
            )
            .map(project -> projectMapper.toProjectResponse((Project) project))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<ProjectResponse> getActiveProjects(@Valid GetByDateRequest request) {
        LocalDateTime startDateTime = Optional.ofNullable(request.getDate())
                                            .orElse(LocalDate.now().atStartOfDay());

        // Perform the query and map results
        return projectRepository.findActiveEvents(
                    request.getClassId(),
                    request.getSubclassCode(),
                    startDateTime,
                    startDateTime,
                    EventType.PROJECT)
            .map(project -> projectMapper.toProjectResponse((Project) project))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<ProjectResponse> updateProject(String id, ProjectUpdateRequest request) {
        return projectRepository.findById(id)
            .map(project -> projectMapper.toProject((Project) project, request))
            .flatMap(this::saveProject)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
    
    /// THÊM DANH SÁCH ĐỀ TÀI
    public Mono<ProjectResponse> addTopics(String projectId, ProjectAddTopicRequest request) {
        return projectRepository.findById(projectId)    
            .map(response -> {
                if (response instanceof Project project) {
                    project.setTopics(request.getTopics());
                }
                return response;
            })
            .flatMap(projectRepository::save)
            .map(project -> projectMapper.toProjectResponse((Project) project))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    /// ĐỀ XUẤT ĐỀ TÀI
    public Mono<ProjectResponse> suggestTopic(String projectId, TopicSuggestionRequest request) {
        return projectRepository.findById(projectId)    
            .map(project -> {
                Topic newTopic = projectMapper.toTopic(request);
                newTopic.setStatus(TopicStatus.PENDING);
                ((Project) project).getTopics().add(newTopic);
                return project;
            })
            .flatMap(projectRepository::save)
            .map(project -> projectMapper.toProjectResponse((Project) project))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    /// DUYỆT ĐỀ TÀI
    public Mono<ProjectResponse> approveTopic(String projectId, TopicApprovalRequest request) {
        return projectRepository.findById(projectId)    
            .map(project -> {
                if (project instanceof Project castedProject) {
                    for (Topic topic : castedProject.getTopics()) {
                        if (request.getTopicId().equals(topic.getId())) {
                            topic.setFeedback(request.getFeedback());
                            if (request.isApproved()) {
                                topic.setStatus(TopicStatus.APPROVED);
                            } else {
                                topic.setStatus(TopicStatus.PROCESSING);
                            }
                            break;
                        }
                    }
                }
                return project;
            })
            .flatMap(projectRepository::save)
            .map(project -> projectMapper.toProjectResponse((Project) project))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
    
    /// ĐĂNG KÝ ĐỀ TÀI
    public Mono<ProjectResponse> registerTopic(String projectId, ProjectChooseTopicRequest request) {
        return projectRepository.findById(projectId)
            .flatMap(response -> {
                if (response instanceof Project project) {
                    Topic matchedOption = project.getTopics().stream()
                        .filter(topic -> topic.getId().equals(request.getTopicId()))
                        .findFirst()
                        .orElse(null);
    
                    if (matchedOption == null) {
                        return Mono.error(new DataNotFoundException());
                    }
    
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
                }
                return Mono.error(new DataNotFoundException());
            })
            .flatMap(response -> {
                // Xét trường hợp đăng ký nhóm mới
                if (request.getGroup() != null) {
                    eventGroupingService.addGroupToEventGrouping(
                        EventGroupingAddGroupRequest.builder()
                            .eventId(projectId)
                            .eventType(EventType.PROJECT)
                            .group(request.getGroup())
                            .build()
                    ).subscribe();
                }
                return Mono.just(response);
            })
            .map(projectMapper::toProjectResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
}
