package com.unicore.post_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.post_service.dto.request.CommentRequest;
import com.unicore.post_service.dto.response.CommentResponse;
import com.unicore.post_service.entity.Comment;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CommentMapper {
    CommentResponse toResponse(Comment comment);
    Comment toComment(CommentRequest request);
}
