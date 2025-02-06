package com.unicore.post_service.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.unicore.post_service.entity.Post;
import com.unicore.post_service.enums.PostType;


public interface PostRepository extends MongoRepository<Post, String> {
    @Query("{ 'sourceId': ?0, 'type': ?1, 'publishedDate': { '$lt': ?2 } }")
    Page<Post> findAllBySourceIdAndTypeAndPublishedDateBefore(
        String sourceId, 
        PostType type, 
        LocalDateTime time, 
        Pageable pageable
    );

    @Query("{ 'sourceId': ?0, 'type': ?1, 'publishedDate': { '$gt': ?2 } }")
    Page<Post> findAllUnpublishedBySourceIdAndType(
        String sourceId, 
        PostType type, 
        LocalDateTime time, 
        Pageable pageable
    );
}
