package com.unicore.classevent_service.repository.httpclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.classevent_service.dto.request.CommentRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.CommentResponse;

import reactor.core.publisher.Mono;

@Component
public class PostClient {
    private WebClient webClient;

    public PostClient(@Value("${application.service.post}") String url) {
        this.webClient = WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
    
    
    public Mono<ApiResponse<CommentResponse>> createEventComment(CommentRequest request) {
        return webClient.post()
            .uri("/comments/internal/create") // Adjust the base URL as needed
            .contentType(MediaType.APPLICATION_JSON) // Specify the content type
            .bodyValue(request) // Serialize the request body to JSON
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<CommentResponse>>() {});
        }

    public Mono<ApiResponse<Boolean>> deleteEventComment(String eventId, String commentId) {
        return webClient.delete()
            .uri(uriBuilder -> uriBuilder.path("/comments/internal/{eventId}/{commentId}")
                .build(eventId, commentId))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<Boolean>>() {});
    }
}
