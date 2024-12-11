package com.unicore.classroom_service.repository.httpclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.classroom_service.dto.request.GeneralTestBulkCreationRequest;
import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.response.ApiResponse;
import com.unicore.classroom_service.dto.response.GeneralTestResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ClassEventClient {
    @Value("${application.service.classevent}")
    private String url;

    private final WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:8888/api/v1/classevent")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();

    public Mono<ApiResponse<List<GeneralTestResponse>>> createBulk(GeneralTestBulkCreationRequest request) {
        log.info("YOLOOOOOOOO create bulk" + request.toString());
        return webClient.post()
            .uri("/tests/internal")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request) // Serialize the request body to JSON
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<GeneralTestResponse>>>() {});
    }

    public Mono<ApiResponse<List<GeneralTestResponse>>> getClassTests(GetByClassRequest request){
        log.info("YOLOOOOOOOO get by class" + request.toString());
        return webClient.post()
            .uri("/tests/class")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<GeneralTestResponse>>>() {});
    }
}
