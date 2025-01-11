package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.request.ProjectCreationRequest;
import com.unicore.classevent_service.dto.request.ProjectUpdateRequest;
import com.unicore.classevent_service.dto.request.TopicSuggestionRequest;
import com.unicore.classevent_service.dto.response.ProjectResponse;
import com.unicore.classevent_service.entity.Project;
import com.unicore.classevent_service.entity.Topic;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ProjectMapper {
    public Project toProject(ProjectCreationRequest request);

    public Project updateProject(@MappingTarget Project project, ProjectUpdateRequest request);

    public ProjectResponse toProjectResponse(Project project);

    public Topic toTopic(TopicSuggestionRequest request);
}
