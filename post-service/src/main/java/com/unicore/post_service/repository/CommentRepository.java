package com.unicore.post_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.unicore.post_service.entity.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
    Page<Comment> findAllBySourceId(
        String sourceId,
        Pageable pageable
    );
}
