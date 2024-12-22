package com.unicore.post_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.unicore.post_service.entity.Category;


public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByCode(String code);
}
