package com.unicore.post_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.post_service.dto.response.PostResponse;
import com.unicore.post_service.entity.Post;

@Mapper(componentModel=ComponentModel.SPRING)
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
