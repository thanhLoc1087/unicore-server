package com.unicore.classroom_service.repository.httpclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.classroom_service.dto.request.GeneralTestBulkCreationRequest;
import com.unicore.classroom_service.dto.response.ApiResponse;
import com.unicore.classroom_service.dto.response.GeneralTestResponse;

import reactor.core.publisher.Mono;

@Component
public class ClassEventClient {
    @Value("${application.service.classevent}")
    private String url;

    private final WebClient webClient = WebClient.builder()
        .baseUrl(url)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();

    public Mono<ApiResponse<List<GeneralTestResponse>>> createBulk(GeneralTestBulkCreationRequest request) {
        return webClient.post()
            .uri("/internal") // Adjust the base URL as needed
            .contentType(MediaType.APPLICATION_JSON) // Specify the content type
            .bodyValue(request) // Serialize the request body to JSON
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<GeneralTestResponse>>>() {});
    }
}
