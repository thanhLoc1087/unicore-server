package com.unicore.profile_service.repository.httpclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.profile_service.dto.request.GetStudentListByClass;
import com.unicore.profile_service.dto.response.ApiResponse;
import com.unicore.profile_service.dto.response.StudentListResponse;

import reactor.core.publisher.Mono;

@Component
public class ClassroomClient {
    private WebClient webClient;

    public ClassroomClient(@Value("${application.service.classroom}") String url) {
        this.webClient = WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Mono<ApiResponse<StudentListResponse>> getClassStudentList(@RequestBody GetStudentListByClass request) {
        return webClient.post()
            .uri("/get/students") // Adjust the base URL as needed
            .contentType(MediaType.APPLICATION_JSON) // Specify the content type
            .bodyValue(request) // Serialize the request body to JSON
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<StudentListResponse>>() {});
        }
        
}
