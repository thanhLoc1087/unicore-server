package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.request.HomeworkCreationRequest;
import com.unicore.classevent_service.dto.request.HomeworkUpdateRequest;
import com.unicore.classevent_service.dto.response.HomeworkResponse;
import com.unicore.classevent_service.entity.Homework;

@Mapper(componentModel = ComponentModel.SPRING)
public interface HomeworkMapper {

    public Homework toHomework(HomeworkCreationRequest request);
    
    public Homework toHomework(@MappingTarget Homework homework, HomeworkUpdateRequest request);

    public HomeworkResponse toHomeworkResponse(Homework homework);
}
