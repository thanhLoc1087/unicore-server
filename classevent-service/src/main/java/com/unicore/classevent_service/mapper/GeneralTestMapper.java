package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.response.GeneralTestResponse;
import com.unicore.classevent_service.entity.GeneralTest;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GeneralTestMapper {
    public GeneralTestResponse toResponse(GeneralTest test);
}
