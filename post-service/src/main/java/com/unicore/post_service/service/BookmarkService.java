package com.unicore.post_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unicore.post_service.dto.request.BookmarkRequest;
import com.unicore.post_service.dto.response.PostResponse;
import com.unicore.post_service.entity.Bookmark;
import com.unicore.post_service.repository.BookmarkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final PostService postService;
    
    public Bookmark createBookmark(BookmarkRequest request) {
        return bookmarkRepository.save(request.toBookmark());
    }

    public void deleteBookmark(String id) {
        bookmarkRepository.deleteById(id);
    }

    public Void deleteBookmarkByEvent(BookmarkRequest request) {
        return bookmarkRepository.deleteByUserEmailAndPostId(request.getUserEmail(), request.getPostId());
    }

    public List<PostResponse> getAllUserBookmarks(String userEmail) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserEmail(userEmail);
        List<String> postIds = bookmarks.stream().map(Bookmark::getPostId).toList();
        return postService.getPostsByIds(postIds);
    }
}
