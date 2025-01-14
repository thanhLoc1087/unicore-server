package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.request.InternStudentRequest;
import com.unicore.classevent_service.entity.InternTopic;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TopicMapper {
    InternTopic toInternTopic(InternStudentRequest request);
    InternTopic updateInternTopic(@MappingTarget InternTopic topic, InternStudentRequest request);
}
