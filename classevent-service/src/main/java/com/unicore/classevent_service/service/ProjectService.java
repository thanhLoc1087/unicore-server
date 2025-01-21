package com.unicore.classevent_service.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.GroupRequest;
import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetByDateRequest;
import com.unicore.classevent_service.dto.request.ProjectAddTopicRequest;
import com.unicore.classevent_service.dto.request.ProjectChooseTopicRequest;
import com.unicore.classevent_service.dto.request.ProjectCreationRequest;
import com.unicore.classevent_service.dto.request.ProjectTopicRequest;
import com.unicore.classevent_service.dto.request.ProjectUpdateRequest;
import com.unicore.classevent_service.dto.request.TopicApprovalRequest;
import com.unicore.classevent_service.dto.request.TopicRegisterScheduleRequest;
import com.unicore.classevent_service.dto.response.ProjectResponse;
import com.unicore.classevent_service.entity.Group;
import com.unicore.classevent_service.entity.Project;
import com.unicore.classevent_service.entity.ProjectTopic;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.TopicStatus;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.exception.InvalidRequestException;
import com.unicore.classevent_service.mapper.ProjectMapper;
import com.unicore.classevent_service.mapper.TopicMapper;
import com.unicore.classevent_service.repository.BaseEventRepository;
import com.unicore.classevent_service.repository.GroupingScheduleRepository;
import com.unicore.classevent_service.repository.TopicRepository;

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
    private final TopicRepository topicRepository;
    private final ProjectMapper projectMapper;
    private final TopicMapper topicMapper;

    private final GroupingService groupingService;

    private final GroupingScheduleRepository groupingScheduleRepository;

    public Mono<ProjectResponse> createProject(ProjectCreationRequest request) {
        return Mono.just(request.getSubclassCode())
            .flatMap(subclassCode -> 
                groupingScheduleRepository
                        .findByClassIdAndSubclassCodeAndIsDefaultTrue(request.getClassId(), subclassCode)
            )
            .map(grouping -> {
                Project project = projectMapper.toProject(request);
                project.setGroupingId(grouping.getId());
                return project;
            })
            .switchIfEmpty(Mono.just(projectMapper.toProject(request)))
            .map(project -> {
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
            .map(project -> projectMapper.updateProject((Project) project, request))
            .flatMap(this::saveProject)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    // Tao lich dag ky de tai
    public Mono<ProjectResponse> createRegisterTopicSchedule(String projectId, TopicRegisterScheduleRequest request) {
        return projectRepository.findById(projectId)
            .flatMap(event -> {
                if (event instanceof Project project) {
                    if (!request.isUseDefaultGroups()) {
                        if ( request.getStartRegisterDate() == null ||
                            request.getEndRegisterDate() == null) {
                            return Mono.error(new InvalidRequestException("Missing startRegisterDate or endRegisterDate."));
                        }
                        return groupingService.createEventGroupingSchedule(projectId, request)
                            .map(grouping -> {
                                project.setGroupingId(grouping.getId());
                                project.setStartTopicRegisterTime(request.getStartRegisterDate());
                                project.setEndTopicRegisterTime(request.getEndRegisterDate());
                                return project;
                            })
                            .flatMap(projectRepository::save)
                            .map(projectMapper::toProjectResponse);
                    } else {
                        if (project.getGroupingId() == null || project.getGroupingId().isEmpty()) {
                            return Mono.error(new InvalidRequestException("This class has no default group."));
                        }
                        project.setStartTopicRegisterTime(request.getStartRegisterDate());
                        project.setEndTopicRegisterTime(request.getEndRegisterDate());
                        return projectRepository.save(project)
                            .map(projectMapper::toProjectResponse);
                    }
                } else {
                    return Mono.error(new DataNotFoundException());
                }
            })
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    /// THÊM DANH SÁCH ĐỀ TÀI
    public Flux<ProjectTopic> addTopics(String projectId, ProjectAddTopicRequest request) {
        return projectRepository.findById(projectId)
            .switchIfEmpty(Mono.error(new InvalidRequestException("project not found")))
            .flatMapMany(project -> Flux.fromIterable(request.getTopics())
                .flatMap(topic -> {
                    ProjectTopic projectTopic = topicMapper.toProjectTopic(topic);
                    projectTopic.genId();
                    projectTopic.setProjectId(project.getId());
                    projectTopic.setStatus(TopicStatus.APPROVED);
                    projectTopic.setInGroup(project.isInGroup());

                    if (topic.getGroupRequest() != null) {
                        return groupingService.createGroup(new GroupRequest(
                                project.getGroupingId(), 
                                topic.getGroupRequest().getName(), 
                                topic.getGroupRequest().getMembers())
                            )
                            .map(group -> {
                                projectTopic.setSelectorId(group.getId());
                                return projectTopic;
                            });
                    } else {
                        projectTopic.setSelectorId(topic.getStudentId());
                    }

                    return Mono.just(projectTopic);
                })
                .flatMap(topicRepository::save)
            );
    }

    /// ĐỀ XUẤT ĐỀ TÀI
    public Mono<ProjectTopic> suggestTopic(String projectId, ProjectTopicRequest request) {
        return projectRepository.findById(projectId)    
            .flatMap(event -> {
                if (event instanceof Project project) {
                    if (project.isAllowTopicSuggestion()) {
                        return Mono.error(new InvalidRequestException("This project does not allow suggestion"));
                    }
                    if (project.getStartTopicRegisterTime().isAfter(LocalDateTime.now())) {
                        return Mono.error(new InvalidRequestException("It is not time for topic registration yet"));
                    }
                    if (project.getEndTopicRegisterTime().isBefore(LocalDateTime.now())) {
                        return Mono.error(new InvalidRequestException("Topic registration is overdue"));
                    }
                } else {
                    return Mono.error(new InvalidRequestException("Project not found"));
                }
                ProjectTopic projectTopic = topicMapper.toProjectTopic(request);
                projectTopic.genId();
                projectTopic.setProjectId(event.getId());
                projectTopic.setStatus(
                    request.getTeacherCodes().isEmpty() ?
                        TopicStatus.PENDING : TopicStatus.TEACHER_ASSIGNED
                );
                projectTopic.setInGroup(event.isInGroup());
                
                if (request.getGroupRequest() != null) {
                    return groupingService.createGroup(new GroupRequest(
                        project.getGroupingId(), 
                        request.getGroupRequest().getName(), 
                        request.getGroupRequest().getMembers())
                    )
                    .map(group -> {
                        projectTopic.setSelectorId(group.getId());
                        return projectTopic;
                    });
                } else {
                    projectTopic.setSelectorId(request.getStudentId());
                }
                return Mono.just(projectTopic);
            })
            .flatMap(topicRepository::save)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    /// DUYỆT ĐỀ TÀI
    public Mono<ProjectTopic> approveTopic(TopicApprovalRequest request) {
        return topicRepository.findById(request.getTopicId())
            .flatMap(topic -> {
                if (topic instanceof ProjectTopic projectTopic) {
                    if (topic.getStatus() == TopicStatus.APPROVED)
                        return Mono.error(new InvalidRequestException("This topic has already been approved."));
                    if (request.isApproved()) {
                        projectTopic.setStatus(TopicStatus.APPROVED);
                    } else {
                        projectTopic.setStatus(TopicStatus.TURNED_DOWN);
                        projectTopic.setTurnDownReason(request.getFeedback());
                    }

                    return topicRepository.save(projectTopic);
                }
                return Mono.error(new InvalidRequestException("This topic does not belong to a project."));
            })
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
    
    /// ĐĂNG KÝ ĐỀ TÀI
    public Mono<ProjectTopic> registerTopic(ProjectChooseTopicRequest request) {
        return projectRepository.findById(request.getProjectId()) 
            .flatMap(event -> {
                if (event instanceof Project project) {
                    if (project.getStartTopicRegisterTime().isAfter(LocalDateTime.now())) {
                        return Mono.error(new InvalidRequestException("It is not time for topic registration yet"));
                    }
                    if (project.getEndTopicRegisterTime().isBefore(LocalDateTime.now())) {
                        return Mono.error(new InvalidRequestException("Topic registration is overdue"));
                    }
                    if (project.isInGroup()) {
                        return groupingService.getGroupingById(project.getGroupingId())
                            .flatMap(grouping -> {
                                GroupRequest groupRequest = new GroupRequest(
                                    grouping.getId(), 
                                    request.getGroup().getName(), 
                                    request.getGroup().getMembers()
                                );
                                return groupingService.internalCreateGroup(groupRequest)
                                    .map(Group::getId);
                            });
                    }
                    return Mono.just(request.getSelector());
                }
                return Mono.error(new InvalidRequestException("Cant find project"));
            })
            .flatMap(selectorId ->  topicRepository.findById(request.getTopicId())
                .flatMap(topic -> {
                    if (topic instanceof ProjectTopic projectTopic) {
                        if (projectTopic.getSelectorId() != null) {
                            return Mono.error(new InvalidRequestException("Topic has already been selected by another group."));
                        }
                        projectTopic.setSelectorId(selectorId);
                        return topicRepository.save(topic)
                            .map(ProjectTopic.class::cast);
                    }
                    return Mono.error(new InvalidRequestException("Topic not found"));
                })
            );
    }

    public Flux<ProjectTopic> getTopicsByProjectId(String projectId) {
        return topicRepository.findAllByProjectId(projectId)
            .map(ProjectTopic.class::cast);
    }

    public Flux<ProjectResponse> getByTopicIds(List<String> topicIds) {
        return projectRepository.findProjectsByTopicIds(topicIds)
            .map(project -> projectMapper.toProjectResponse((Project) project));
    }
}
