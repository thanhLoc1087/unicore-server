package com.unicore.classevent_service.service;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.BookmarkRequest;
import com.unicore.classevent_service.entity.BaseEvent;
import com.unicore.classevent_service.entity.Bookmark;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.repository.BaseEventRepository;
import com.unicore.classevent_service.repository.BookmarkRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository repository;
    private final BaseEventRepository baseEventRepository;
    
    public Mono<Bookmark> createBookmark(BookmarkRequest request) {
        return repository.save(request.toBookmark());
    }

    public Mono<Void> deleteBookmark(String id) {
        return repository.deleteById(id);
    }

    public Mono<Void> deleteBookmarkByEvent(BookmarkRequest request) {
        return repository.deleteByUserEmailAndEventId(request.getUserEmail(), request.getEventId());
    }

    public Flux<BaseEvent> getAllUserBookmarks(String userEmail) {
        return repository.findAllByUserEmail(userEmail)
            .flatMap(bookmark -> baseEventRepository.findById(bookmark.getEventId()));
    }

    public Flux<BaseEvent> getUserBookmarksByEventType(String userEmail, EventType eventType) {
        return repository.findAllByUserEmailAndEventType(userEmail, eventType)
            .flatMap(bookmark -> baseEventRepository.findById(bookmark.getEventId()));
    }
}
