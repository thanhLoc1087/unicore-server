package com.unicore.post_service.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.post_service.dto.request.CommentRequest;
import com.unicore.post_service.dto.response.ApiResponse;
import com.unicore.post_service.dto.response.CommentResponse;
import com.unicore.post_service.dto.response.PageResponse;
import com.unicore.post_service.service.CommentService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ApiResponse<CommentResponse> createPostComment(@RequestBody CommentRequest commentRequest) {
        return ApiResponse.<CommentResponse>builder()
            .data(commentService.createPostComment(commentRequest))
            .time(LocalDateTime.now())
            .build();
    }

    @PostMapping("/internal/create")
    public ApiResponse<CommentResponse> createEventComment(@RequestBody CommentRequest commentRequest) {
        return ApiResponse.<CommentResponse>builder()
            .data(commentService.createComment(commentRequest))
            .time(LocalDateTime.now())
            .build();
    }
    
    @GetMapping
    public ApiResponse<PageResponse<CommentResponse>> getProjectUnpublishedPosts(
        @RequestParam(value="source_id", required=true) String sourceId,
        @RequestParam(value="page", defaultValue="1", required=false) int page,
        @RequestParam(value="size", defaultValue="10", required=false) int size
    ) {
        return ApiResponse.<PageResponse<CommentResponse>>builder()
            .data(commentService.getComments(sourceId, page, size))
            .time(LocalDateTime.now())
            .build();
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deletePostComment(@PathVariable String id) {
        return ApiResponse.<Boolean>builder()
            .data(commentService.deletePostComment(id))
            .time(LocalDateTime.now())
            .build();
    }
    
    @DeleteMapping("/internal/{eventId}/{commentId}")
    public ApiResponse<Boolean> deleteComment(@PathVariable String eventId, @PathVariable String commentId) {
        return ApiResponse.<Boolean>builder()
            .data(commentService.deleteEventComment(eventId, commentId))
            .time(LocalDateTime.now())
            .build();
    }

}
