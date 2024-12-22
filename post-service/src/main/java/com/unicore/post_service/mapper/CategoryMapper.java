package com.unicore.post_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.post_service.dto.request.CategoryRequest;
import com.unicore.post_service.dto.response.CategoryResponse;
import com.unicore.post_service.entity.Category;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);

    Category toCategory(CategoryRequest request);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
