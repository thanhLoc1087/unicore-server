package com.unicore.post_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.post_service.entity.Bookmark;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkRequest {
    @JsonProperty("post_id")
    private String postId;
    @JsonProperty("user_email")
    private String userEmail;

    public Bookmark toBookmark() {
        return Bookmark.builder()
            .userEmail(userEmail)
            .postId(postId)
            .build();
    }
}
