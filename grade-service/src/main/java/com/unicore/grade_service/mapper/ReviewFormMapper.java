package com.unicore.grade_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.grade_service.dto.request.ReviewFormUpdateScoreRequest;
import com.unicore.grade_service.entity.ReviewForm;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ReviewFormMapper {
    void updateForm(@MappingTarget ReviewForm form, ReviewFormUpdateScoreRequest request);
}
