package com.unicore.classroom_service.repository.httpclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.classroom_service.dto.response.ApiResponse;
import com.unicore.classroom_service.dto.response.TeacherResponse;

import reactor.core.publisher.Mono;

@Component
public class ProfileClient {
    private WebClient webClient;

    public ProfileClient(@Value("${application.service.organization}") String url) {
        this.webClient = WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Mono<List<TeacherResponse>> getTeachersByEmails(List<String> emails) {
        return webClient.post()
            .uri("/teachers/emails")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(emails)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<ApiResponse<List<TeacherResponse>>>() {})
            .map(responseEntity -> responseEntity.getBody().getData());
    }
}
