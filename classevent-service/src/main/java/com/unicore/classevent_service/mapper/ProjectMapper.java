package com.unicore.classevent_service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.unicore.classevent_service.dto.request.ProjectCreationRequest;
import com.unicore.classevent_service.dto.request.ProjectUpdateRequest;
import com.unicore.classevent_service.dto.request.TopicSuggestionRequest;
import com.unicore.classevent_service.dto.response.ProjectResponse;
import com.unicore.classevent_service.entity.Project;
import com.unicore.classevent_service.entity.Topic;

@Component
public class ProjectMapper {

    public Project toProject(ProjectCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Project.ProjectBuilder project = Project.builder();

        project.allowGradeReview( request.isAllowGradeReview() );
        project.weight( request.getWeight() );
        project.weightType( request.getWeightType() );
        project.inGroup( request.getInGroup() );
        project.startDate( request.getStartDate() );
        project.allowTopicSuggestion( request.isAllowTopicSuggestion() );
        project.classId( request.getClassId() );
        project.subclassCode( request.getSubclassCode() );
        project.description( request.getDescription() );
        project.name( request.getName() );
        project.reviewTimes( request.getReviewTimes() );
        List<Topic> list = request.getTopics();
        if ( list != null ) {
            project.topics( new ArrayList<>( list ) );
        }

        return project.build();
    }

    public Project toProject(Project project, ProjectUpdateRequest request) {
        if (request == null) {
            return project;
        }
    
        if (request.getAllowGradeReview() != null) {
            project.setAllowGradeReview(request.getAllowGradeReview());
        }
        if (request.getWeight() != null) {
            project.setWeight(request.getWeight());
        }
        if (request.getWeightType() != null) {
            project.setWeightType(request.getWeightType());
        }
        if (request.getStartDate() != null) {
            project.setStartDate(request.getStartDate());
        }
        if (request.getAllowTopicSuggestion() != null) {
            project.setAllowTopicSuggestion(request.getAllowTopicSuggestion());
        }
        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }
        if (request.getInGroup() != null) {
            project.setInGroup(request.getInGroup());
        }
        if (request.getName() != null) {
            project.setName(request.getName());
        }
        if (request.getReviewTimes() != null) {
            project.setReviewTimes(request.getReviewTimes());
        }
        if (request.getTopics() != null) {
            if (project.getTopics() != null) {
                project.getTopics().clear();
                project.getTopics().addAll(request.getTopics());
            } else {
                project.setTopics(new ArrayList<>(request.getTopics()));
            }
        } else if (project.getTopics() != null) {
            project.setTopics(null);
        }
    
        return project;
    }
    


    public ProjectResponse toProjectResponse(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectResponse.ProjectResponseBuilder projectResponse = ProjectResponse.builder();

        projectResponse.weight( project.getWeight() );
        projectResponse.weightType( project.getWeightType() );
        projectResponse.inGroup( project.isInGroup() );
        projectResponse.startDate( project.getStartDate() );
        projectResponse.allowGradeReview( project.isAllowGradeReview() );
        projectResponse.allowTopicSuggestion( project.isAllowTopicSuggestion() );
        projectResponse.classId( project.getClassId() );
        projectResponse.subclassCode( project.getSubclassCode() );
        projectResponse.createdBy( project.getCreatedBy() );
        projectResponse.createdDate( project.getCreatedDate() );
        projectResponse.description( project.getDescription() );
        projectResponse.id( project.getId() );
        projectResponse.modifiedBy( project.getModifiedBy() );
        projectResponse.modifiedDate( project.getModifiedDate() );
        projectResponse.name( project.getName() );
        projectResponse.reviewTimes( project.getReviewTimes() );
        projectResponse.subclassCode( project.getSubclassCode() );
        List<Topic> list = project.getTopics();
        if ( list != null ) {
            projectResponse.topics( new ArrayList<Topic>( list ) );
        }

        return projectResponse.build();
    }

    public Topic toTopic(TopicSuggestionRequest request) {
        if (request == null) return null;

        Topic.TopicBuilder topic = Topic.builder();

        topic.name(request.getName());
        topic.description(request.getDescription());
        topic.teacherCode(request.getTeacherCode());
        topic.teacherName(request.getDescription());
        topic.limit(request.getLimit());

        List<String> list = request.getSelectors();
        if ( list != null ) {
            topic.selectors( new ArrayList<>( list ) );
        }
        
        return topic.build();
    }
}
