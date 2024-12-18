package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.Bookmark;
import com.unicore.classevent_service.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkRequest {
    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("event_type")
    private EventType eventType;

    public Bookmark toBookmark() {
        return Bookmark.builder()
            .userEmail(userEmail)
            .eventId(eventId)
            .eventType(eventType)
            .build();
    }
}
