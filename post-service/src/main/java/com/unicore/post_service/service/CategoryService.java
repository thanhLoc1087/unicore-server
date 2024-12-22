package com.unicore.post_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unicore.post_service.dto.request.CategoryRequest;
import com.unicore.post_service.dto.response.CategoryResponse;
import com.unicore.post_service.entity.Category;
import com.unicore.post_service.exception.AppException;
import com.unicore.post_service.exception.ErrorCode;
import com.unicore.post_service.mapper.CategoryMapper;
import com.unicore.post_service.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryResponse create(CategoryRequest request) {
        Category category = mapper.toCategory(request);
        category.setCode(Category.genCode(category.getName()));
        if (request.isCheckDuplicate()) {
            Category checking = checkDuplicate(request.getName());
            if (checking != null) {
                CategoryResponse response = mapper.toResponse(checking);
                response.setDuplicated(true);
                return response;
            }
        }
        category = repository.save(category);
        return mapper.toResponse(category);
    }

    public Category checkDuplicate(String name) {
        return repository.findByCode(Category.genCode(name));
    }

    public List<CategoryResponse> getAllCategories() {
        return repository.findAll()
            .stream()
            .map(mapper::toResponse)
            .toList();
    }

    public List<CategoryResponse> getAllByIds(List<String> ids) {
        return repository.findAllById(ids)
            .stream()
            .map(mapper::toResponse)
            .toList();
    }

    public CategoryResponse getById(String id) {
        Category category = repository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        return mapper.toResponse(category);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
    
    public CategoryResponse update(String id, CategoryRequest request) {
        Category category = repository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        mapper.updateCategory(category, request);
        category.setCode(Category.genCode(category.getName()));
        if (request.isCheckDuplicate()) {
            Category checking = checkDuplicate(request.getName());
            if (checking != null) {
                CategoryResponse response = mapper.toResponse(checking);
                response.setDuplicated(true);
                return response;
            }
        }
        category = repository.save(category);
        return mapper.toResponse(category);
    }
}
