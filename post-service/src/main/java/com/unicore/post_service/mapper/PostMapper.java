package com.unicore.post_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.post_service.dto.request.PostRequest;
import com.unicore.post_service.dto.response.PostResponse;
import com.unicore.post_service.entity.Post;

@Mapper(componentModel = ComponentModel.SPRING)
public interface PostMapper {
    public PostResponse toPostResponse(Post post);
    public Post toPost(PostRequest request);
    public void updatePost(@MappingTarget Post post, PostRequest request);
}
