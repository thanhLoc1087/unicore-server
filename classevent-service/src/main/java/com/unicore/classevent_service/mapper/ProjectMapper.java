package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.request.ProjectCreationRequest;
import com.unicore.classevent_service.dto.request.ProjectUpdateRequest;
import com.unicore.classevent_service.dto.request.TopicSuggestionRequest;
import com.unicore.classevent_service.dto.response.ProjectResponse;
import com.unicore.classevent_service.dto.response.ProjectTopicResponse;
import com.unicore.classevent_service.entity.Project;
import com.unicore.classevent_service.entity.ProjectTopic;
import com.unicore.classevent_service.entity.ThesisTopic;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ProjectMapper {
    public Project toProject(ProjectCreationRequest request);

    public Project updateProject(@MappingTarget Project project, ProjectUpdateRequest request);

    public ProjectResponse toProjectResponse(Project project);

    public ProjectTopic toProjectTopic(TopicSuggestionRequest request);
    public ThesisTopic toThesisTopic(TopicSuggestionRequest request);

    public ProjectTopicResponse toResponse(ProjectTopic topic);
}
