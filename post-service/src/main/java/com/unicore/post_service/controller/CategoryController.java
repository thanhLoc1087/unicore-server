package com.unicore.post_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.unicore.post_service.dto.request.CategoryRequest;
import com.unicore.post_service.dto.response.ApiResponse;
import com.unicore.post_service.dto.response.CategoryResponse;
import com.unicore.post_service.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
            .data(service.create(request))
            .time(LocalDateTime.now())
            .build();
    }
        
    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getById(@PathVariable String id) {
        return ApiResponse.<CategoryResponse>builder()
            .data(service.getById(id))
            .time(LocalDateTime.now())
            .build();
    }
        
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll() {
        return ApiResponse.<List<CategoryResponse>>builder()
            .data(service.getAllCategories())
            .time(LocalDateTime.now())
            .build();
    }
    
    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> update(@PathVariable String id, @RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
            .data(service.update(id, request))
            .time(LocalDateTime.now())
            .build();
    }

    @DeleteMapping("/{id}") 
    public ApiResponse<String> delete(@PathVariable String id) {
        service.deleteById(id);
        return ApiResponse.<String>builder()
            .data("Success")
            .time(LocalDateTime.now())
            .build();
    }
}
