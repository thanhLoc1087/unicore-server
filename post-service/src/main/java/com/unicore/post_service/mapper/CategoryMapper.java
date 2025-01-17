package com.unicore.post_service.mapper;

import org.springframework.stereotype.Component;

import com.unicore.post_service.dto.request.CategoryRequest;
import com.unicore.post_service.dto.response.CategoryResponse;
import com.unicore.post_service.entity.Category;

@Component
public class CategoryMapper {
    public CategoryResponse toResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.id( category.getId() );
        categoryResponse.name( category.getName() );

        return categoryResponse.build();
    }

    public Category toCategory(CategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( request.getName() );

        return category.build();
    }

    public void updateCategory(Category category, CategoryRequest request) {
        if ( request == null ) {
            return;
        }

        category.setName( request.getName() );
    }
}
