package com.unicore.classevent_service.repository.httpclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.ClassroomResponse;
import com.unicore.classevent_service.dto.response.StudentInClassResponse;
import com.unicore.classevent_service.dto.response.StudentResponse;

import reactor.core.publisher.Mono;

@Component
public class ProfileClient {
    private WebClient webClient;

    public ProfileClient(@Value("${application.service.profile}") String url) {
        this.webClient = WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Mono<ApiResponse<StudentInClassResponse>> getClassStudents(GetByClassRequest request) {
        return webClient.post()
            .uri("/students/class") // Adjust the base URL as needed
            .contentType(MediaType.APPLICATION_JSON) // Specify the content type
            .bodyValue(request) // Serialize the request body to JSON
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<StudentInClassResponse>>() {});
        }

    
    public Mono<StudentResponse> getStudentByCode(String code) {
        return webClient.get()
            .uri("/students/code/{code}", code)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<ApiResponse<StudentResponse>>() {})
            // .bodyToMono(new ParameterizedTypeReference<ApiResponse<SubjectResponse>>() {})
            .map(responseEntity -> responseEntity.getBody().getData());
    }
}
