package com.unicore.classevent_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classevent_service.dto.request.BookmarkRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.entity.BaseEvent;
import com.unicore.classevent_service.entity.Bookmark;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.service.BookmarkService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService service;

    @PostMapping
    public Mono<ApiResponse<Bookmark>> create(@RequestBody BookmarkRequest request) {
        return service.createBookmark(request)
            .map(bookmark -> new ApiResponse<>(
                bookmark, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @DeleteMapping("/{id}")
    public Mono<ApiResponse<String>> delete(@PathVariable String id) {
        return service.deleteBookmark(id)
            .then(Mono.just(new ApiResponse<>(
                HttpStatus.OK.toString(),
                ApiMessage.SUCCESS.getMessage(),
                HttpStatus.OK.value(),
                LocalDateTime.now()
            )));
    }
    
    @PostMapping("/delete")
    public Mono<ApiResponse<String>> deleteByEvent(@RequestBody BookmarkRequest request) {
        return service.deleteBookmarkByEvent(request)
            .then(Mono.just(new ApiResponse<>(
                HttpStatus.OK.toString(),
                ApiMessage.SUCCESS.getMessage(),
                HttpStatus.OK.value(),
                LocalDateTime.now()
            )));
    }
    
    @GetMapping("/user/{userEmail}")
    public Mono<ApiResponse<List<BaseEvent>>> getUserBookmarks(@PathVariable String userEmail) {
        return service.getAllUserBookmarks(userEmail)
            .collectList()
            .map(bookmarks -> new ApiResponse<>(
                bookmarks, 
                ApiMessage.SUCCESS.getMessage() + (bookmarks.isEmpty() ? " - Empty list" : ""), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }

    @GetMapping("/user/{userEmail}/{type}")
    public Mono<ApiResponse<List<BaseEvent>>> getUserBookmarksByType(@PathVariable String userEmail, @PathVariable EventType type) {
        return service.getUserBookmarksByEventType(userEmail, type)
            .collectList()
            .map(bookmarks -> new ApiResponse<>(
                bookmarks, 
                ApiMessage.SUCCESS.getMessage() + (bookmarks.isEmpty() ? " - Empty list" : ""), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
}
