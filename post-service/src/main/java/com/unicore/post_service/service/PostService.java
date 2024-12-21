package com.unicore.post_service.service;

import java.time.Instant;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.unicore.post_service.dto.request.PostRequest;
import com.unicore.post_service.dto.response.PageResponse;
import com.unicore.post_service.dto.response.PostResponse;
import com.unicore.post_service.dto.response.ProfileResponse;
import com.unicore.post_service.entity.Post;
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
    DateTimeFormatter dateTimeFormatter;
    ProfileClient profileClient;

    public PostResponse createPost(PostRequest request) {
        // var authentication = SecurityContextHolder.getContext().getAuthentication();

        Post post = Post.builder()
            .userId("LocLoc")
            // .userId(authentication.getName())
            .content(request.getContent())
            .createdDate(Instant.now())
            .modifiedDate(Instant.now())
            .build();

        post = postRepository.save(post);
        
        return postMapper.toPostResponse(post);
    }

    // public PageResponse<PostResponse> getMyPosts(int page, int size) {
        // var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        // ProfileResponse userProfile = null;
        // userProfile = profileClient.getProfile(userId).getResult();
        // try {
        // } catch (Exception e) {
            
        // }

        // Sort sort = Sort.by("createdDate").descending();
        // Pageable pageable = PageRequest.of(page - 1, size, sort);

        // var pageData = postRepository.findAllByUserId(userId, pageable);
        // String username = userProfile != null ? userProfile.getUsername(): null;
        // log.info("hello" + username);

        // var postsList = pageData.getContent().stream().map(post -> {
        //     var postResponse = postMapper.toPostResponse(post);
        //     postResponse.setCreated(dateTimeFormatter.format(post.getCreatedDate()));
        //     postResponse.setUsername(username);
        //     return postResponse;
        // }).toList();

        // return PageResponse.<PostResponse>builder()
        //     .currentPage(page)
        //     .pageSize(pageData.getSize())
        //     .totalPages(pageData.getTotalPages())
        //     .totalElements(pageData.getTotalElements())
        //     .data(postsList)
        //     .build();
    // }
}
