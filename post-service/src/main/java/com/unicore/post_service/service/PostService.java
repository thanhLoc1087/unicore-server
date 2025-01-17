package com.unicore.post_service.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.unicore.post_service.dto.request.PostRequest;
import com.unicore.post_service.dto.response.PageResponse;
import com.unicore.post_service.dto.response.PostResponse;
import com.unicore.post_service.entity.Editor;
import com.unicore.post_service.entity.Post;
import com.unicore.post_service.enums.PostType;
import com.unicore.post_service.exception.AppException;
import com.unicore.post_service.exception.ErrorCode;
import com.unicore.post_service.mapper.PostMapper;
import com.unicore.post_service.repository.PostRepository;
import com.unicore.post_service.repository.httpClient.ProfileClient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PostService {

    PostRepository postRepository;
    PostMapper postMapper;

    CategoryService categoryService;

    DateTimeFormatter dateTimeFormatter;
    ProfileClient profileClient;

    public PostResponse createPost(PostRequest request, PostType type) {
        // var authentication = SecurityContextHolder.getContext().getAuthentication();

        Post post = Post.builder()
            .sourceId(request.getSourceId())
            .name(request.getName())
            .createdDate(LocalDateTime.now())
            .createdBy(request.getCreatedBy())
            .creatorEmail(request.getCreatorEmail())
            .type(type)
            // .userId(authentication.getName())
            .description(request.getDescription())
            .build();

        post = postRepository.save(post);

        PostResponse response = postMapper.toPostResponse(post);
        response.setCategories(categoryService.getAllByIds(post.getCategoryIds()));
        
        return response;
    }

    public PostResponse updatePost(String postId, PostRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        postMapper.updatePost(post, request);
        post.getModifiedBy().add(new Editor(request.getCreatedBy(), request.getCreatorEmail(), Instant.now()));
        post = postRepository.save(post);

        PostResponse response = postMapper.toPostResponse(post);
        response.setCategories(categoryService.getAllByIds(post.getCategoryIds()));
        
        return response;
    }

    public List<PostResponse> getPostsByIds(List<String> ids) {
        return postRepository.findAllById(ids)
            .stream().map(postMapper::toPostResponse).toList();
    }

    public PageResponse<PostResponse> getPosts(String sourceId, int page, int size, PostType type) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        var pageData = postRepository.findAllBySourceIdAndType(sourceId, type, pageable);
        
        var postsList = pageData.getContent().stream().map(post -> {
            var postResponse = postMapper.toPostResponse(post);
            postResponse.setCreatedDate(dateTimeFormatter.format(post.getCreatedDate().toInstant(ZoneOffset.UTC)));
            return postResponse;
        }).toList();

        return PageResponse.<PostResponse>builder()
            .currentPage(page)
            .pageSize(pageData.getSize())
            .totalPages(pageData.getTotalPages())
            .totalElements(pageData.getTotalElements())
            .data(postsList)
            .build();
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }
}
