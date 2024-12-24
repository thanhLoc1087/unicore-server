package com.unicore.post_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.post_service.dto.request.BookmarkRequest;
import com.unicore.post_service.dto.response.ApiResponse;
import com.unicore.post_service.dto.response.PostResponse;
import com.unicore.post_service.entity.Bookmark;
import com.unicore.post_service.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {
    
    private final BookmarkService service;

    @PostMapping
    public ApiResponse<Bookmark> create(@RequestBody BookmarkRequest request) {
        return ApiResponse.<Bookmark>builder()
            .data(service.createBookmark(request))
            .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        service.deleteBookmark(id);
        return ApiResponse.<String>builder()
            .data("Success")
            .build();
    }
    
    @PostMapping("/delete")
    public ApiResponse<String> deleteByEvent(@RequestBody BookmarkRequest request) {
        service.deleteBookmarkByEvent(request);
        return ApiResponse.<String>builder()
            .data("Success")
            .build();
    }
    
    @GetMapping("/user/{userEmail}")
    public ApiResponse<List<PostResponse>> getUserBookmarks(@PathVariable String userEmail) {
        return ApiResponse.<List<PostResponse>>builder()
            .data(service.getAllUserBookmarks(userEmail))
            .build();
    }
}
