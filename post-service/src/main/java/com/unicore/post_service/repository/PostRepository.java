package com.unicore.post_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.unicore.post_service.entity.Post;
import com.unicore.post_service.enums.PostType;


public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findAllBySourceIdAndType(String sourceId, PostType type, Pageable pageable);
}
