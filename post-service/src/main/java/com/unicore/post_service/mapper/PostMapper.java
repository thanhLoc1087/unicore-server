package com.unicore.post_service.mapper;

import org.mapstruct.Mapper;

import com.unicore.post_service.dto.response.PostResponse;
import com.unicore.post_service.entity.Post;

@Mapper(componentModel="spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
