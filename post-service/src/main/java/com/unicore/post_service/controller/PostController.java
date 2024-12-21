package com.unicore.post_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.post_service.dto.request.PostRequest;
import com.unicore.post_service.dto.response.ApiResponse;
import com.unicore.post_service.dto.response.PageResponse;
import com.unicore.post_service.dto.response.PostResponse;
import com.unicore.post_service.service.PostService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;

    @PostMapping("/create")
    public ApiResponse<PostResponse> createPost(@RequestBody PostRequest request) {
        return ApiResponse.<PostResponse>builder()
            .result(postService.createPost(request))
            .build();
    }
    
    // @GetMapping("/my-posts")
    // public ApiResponse<PageResponse<PostResponse>> getMyPosts(
    //     @RequestParam(value="page", defaultValue="1", required=false) int page,
    //     @RequestParam(value="size", defaultValue="10", required=false) int size
    // ) {
    //     return ApiResponse.<PageResponse<PostResponse>>builder()
    //         .result(postService.getMyPosts(page, size))
    //         .build();
    // }
}