package com.unicore.post_service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.unicore.post_service.entity.Bookmark;

public interface BookmarkRepository extends MongoRepository<Bookmark, String> {
    List<Bookmark> findAllByUserEmail(String userEmail);
    Void deleteByUserEmailAndPostId(String userEmail, String postId);
}
