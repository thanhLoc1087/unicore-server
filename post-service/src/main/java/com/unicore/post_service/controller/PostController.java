package com.unicore.post_service.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.post_service.dto.request.PostRequest;
import com.unicore.post_service.dto.response.ApiResponse;
import com.unicore.post_service.dto.response.PageResponse;
import com.unicore.post_service.dto.response.PostResponse;
import com.unicore.post_service.enums.PostType;
import com.unicore.post_service.service.PostService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPostById(@PathVariable String id) {
        return ApiResponse.<PostResponse>builder()
            .data(postService.getById(id))
            .time(LocalDateTime.now())
            .build();
    }

    @PostMapping("/class/create")
    public ApiResponse<PostResponse> createClassPost(@RequestBody PostRequest request) {
        return ApiResponse.<PostResponse>builder()
            .data(postService.createPost(request, PostType.CLASS))
            .time(LocalDateTime.now())
            .build();
    }

    @PostMapping("/org/create")
    public ApiResponse<PostResponse> createOrgPost(@RequestBody PostRequest request) {
        return ApiResponse.<PostResponse>builder()
            .data(postService.createPost(request, PostType.ORG))
            .time(LocalDateTime.now())
            .build();
    }

    @PostMapping("/event/create")
    public ApiResponse<PostResponse> createProjectPost(@RequestBody PostRequest request) {
        return ApiResponse.<PostResponse>builder()
            .data(postService.createPost(request, PostType.PROJECT))
            .time(LocalDateTime.now())
            .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<PostResponse> updatePost(@PathVariable String id, @RequestBody PostRequest request) {
        return ApiResponse.<PostResponse>builder()
            .data(postService.updatePost(id, request))
            .time(LocalDateTime.now())
            .build();
    }
    
    @GetMapping("/org")
    public ApiResponse<PageResponse<PostResponse>> getOrgPosts(
        @RequestParam(value="org_id", required=true) String orgId,
        @RequestParam(value="page", defaultValue="1", required=false) int page,
        @RequestParam(value="size", defaultValue="10", required=false) int size
    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
            .data(postService.getPosts(orgId, page, size, PostType.ORG))
            .time(LocalDateTime.now())
            .build();
    }
    
    @GetMapping("/org/unpublished")
    public ApiResponse<PageResponse<PostResponse>> getOrgUnpublishedPosts(
        @RequestParam(value="org_id", required=true) String orgId,
        @RequestParam(value="page", defaultValue="1", required=false) int page,
        @RequestParam(value="size", defaultValue="10", required=false) int size
    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
            .data(postService.getUnpublishedPosts(orgId, page, size, PostType.ORG))
            .time(LocalDateTime.now())
            .build();
    }

    @GetMapping("/class")
    public ApiResponse<PageResponse<PostResponse>> getClassPosts(
        @RequestParam(value="class_id", required=true) String classId,
        @RequestParam(value="page", defaultValue="1", required=false) int page,
        @RequestParam(value="size", defaultValue="10", required=false) int size
    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
            .data(postService.getPosts(classId, page, size, PostType.CLASS))
            .time(LocalDateTime.now())
            .build();
    }

    @GetMapping("/class/unpublished")
    public ApiResponse<PageResponse<PostResponse>> getClassUnpublishedPosts(
        @RequestParam(value="class_id", required=true) String classId,
        @RequestParam(value="page", defaultValue="1", required=false) int page,
        @RequestParam(value="size", defaultValue="10", required=false) int size
    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
            .data(postService.getUnpublishedPosts(classId, page, size, PostType.CLASS))
            .time(LocalDateTime.now())
            .build();
    }

    @GetMapping("/project")
    public ApiResponse<PageResponse<PostResponse>> getProjectPosts(
        @RequestParam(value="event_id", required=true) String eventId,
        @RequestParam(value="page", defaultValue="1", required=false) int page,
        @RequestParam(value="size", defaultValue="10", required=false) int size
    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
            .data(postService.getPosts(eventId, page, size, PostType.PROJECT))
            .time(LocalDateTime.now())
            .build();
        }

    @GetMapping("/project/unpublished")
    public ApiResponse<PageResponse<PostResponse>> getProjectUnpublishedPosts(
        @RequestParam(value="event_id", required=true) String eventId,
        @RequestParam(value="page", defaultValue="1", required=false) int page,
        @RequestParam(value="size", defaultValue="10", required=false) int size
    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
            .data(postService.getUnpublishedPosts(eventId, page, size, PostType.PROJECT))
            .time(LocalDateTime.now())
            .build();
        }
        
    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ApiResponse.<String>builder()
            .data("Success")
            .time(LocalDateTime.now())
            .build();
    }
}