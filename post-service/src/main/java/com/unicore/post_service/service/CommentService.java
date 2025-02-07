package com.unicore.post_service.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unicore.post_service.dto.request.CommentRequest;
import com.unicore.post_service.dto.response.CommentResponse;
import com.unicore.post_service.dto.response.PageResponse;
import com.unicore.post_service.entity.Comment;
import com.unicore.post_service.entity.Post;
import com.unicore.post_service.exception.AppException;
import com.unicore.post_service.exception.ErrorCode;
import com.unicore.post_service.mapper.CommentMapper;
import com.unicore.post_service.repository.CommentRepository;
import com.unicore.post_service.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final DateTimeFormatter dateTimeFormatter;

    public CommentResponse createPostComment(CommentRequest request) {
        Post post = postRepository.findById(request.getSourceId()).orElseThrow(() ->new AppException(ErrorCode.NOT_FOUND));

        Comment comment = commentMapper.toComment(request);
        comment.setCreatedDate(LocalDateTime.now());
        comment = commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);

        return commentMapper.toResponse(comment);
    }

    public CommentResponse createComment(CommentRequest request) {
        Comment comment = commentMapper.toComment(request);
        comment.setCreatedDate(LocalDateTime.now());
        comment = commentRepository.save(comment);

        return commentMapper.toResponse(comment);
    }

    
    public PageResponse<CommentResponse> getComments(String sourceId, int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        var pageData = commentRepository.findAllBySourceId(sourceId, pageable);
        
        var commentsList = pageData.getContent().stream().map(comment -> {
            var commentResponse = commentMapper.toResponse(comment);
            commentResponse.setCreatedDate(dateTimeFormatter.format(comment.getCreatedDate().toInstant(ZoneOffset.UTC)));
            return commentResponse;
        }).toList();

        return PageResponse.<CommentResponse>builder()
            .currentPage(page)
            .pageSize(pageData.getSize())
            .totalPages(pageData.getTotalPages())
            .totalElements(pageData.getTotalElements())
            .data(commentsList)
            .build();
    }

    public boolean deletePostComment(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            Post post = postRepository.findById(comment.get().getSourceId()).orElseThrow();
            commentRepository.deleteById(id);
            post.setCommentCount(post.getCommentCount() - 1);
            postRepository.save(post);
            return true;
        }
        return false;
    }

    public boolean deleteEventComment(String eventId, String commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent() && comment.get().getSourceId().equalsIgnoreCase(eventId)) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }
}
