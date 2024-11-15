package com.unicore.classroom_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classroom_service.dto.request.CourseworkWeightCreationRequest;
import com.unicore.classroom_service.dto.response.CourseworkWeightResponse;
import com.unicore.classroom_service.entity.CourseworkWeight;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CourseworkWeightMapper {
    public CourseworkWeight toCourseworkWeight(CourseworkWeightCreationRequest request);
    public CourseworkWeightResponse toCourseworkWeightResponse(CourseworkWeight courseworkWeight);
}
