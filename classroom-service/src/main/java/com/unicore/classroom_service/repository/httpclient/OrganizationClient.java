package com.unicore.classroom_service.repository.httpclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.classroom_service.dto.response.SubjectResponse;

import reactor.core.publisher.Mono;

@Component
public class OrganizationClient {
    @Value("${application.service.organization}")
    private String url;

    private final WebClient webClient = WebClient.builder()
        .baseUrl(url)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();

    public Mono<SubjectResponse> getSubject(String code) {
        return webClient.get()
                        .uri("/subjects/code/{code}", code)
                        .retrieve()
                        .bodyToMono(SubjectResponse.class);
    }
}
