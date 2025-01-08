package com.unicore.classroom_service.repository.httpclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.classroom_service.dto.response.ApiResponse;
import com.unicore.classroom_service.dto.response.SubjectResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class OrganizationClient {
    private WebClient webClient;

    public OrganizationClient(@Value("${application.service.organization}") String url) {
        this.webClient = WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Mono<SubjectResponse> getSubject(String code) {
        return webClient.get()
                        .uri("/subjects/code/{code}", code)
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<ApiResponse<SubjectResponse>>() {})
                        // .bodyToMono(new ParameterizedTypeReference<ApiResponse<SubjectResponse>>() {})
                        .map(responseEntity -> responseEntity.getBody().getData());
    }

    public Mono<List<SubjectResponse>> getAllSubjects() {
        return webClient.get()
                        .uri("/subjects")
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<ApiResponse<List<SubjectResponse>>>() {})
                        .map(responseEntity -> {
                            log.info("OrgClient getAllSubjects " + responseEntity.getBody().getData().toString());
                            return responseEntity.getBody().getData();
                        });
    }

}
