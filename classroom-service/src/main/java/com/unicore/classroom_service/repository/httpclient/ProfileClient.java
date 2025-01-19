package com.unicore.classroom_service.repository.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.classroom_service.dto.response.ApiResponse;
import com.unicore.classroom_service.dto.response.StudentResponse;
import com.unicore.classroom_service.dto.response.SubjectResponse;
import com.unicore.classroom_service.dto.response.TeacherResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ProfileClient {
    private WebClient webClient;

    public ProfileClient(@Value("${application.service.profile}") String url) {
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
            .map(responseEntity -> {
                return responseEntity.getBody() != null ? responseEntity.getBody().getData() : new ArrayList<>();
            });
    }

    public Mono<List<TeacherResponse>> getTeachersByCodes(List<String> codes) {
        return webClient.post()
            .uri("/teachers/codes")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(codes)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<ApiResponse<List<TeacherResponse>>>() {})
            .map(responseEntity -> {
                return responseEntity.getBody() != null ? responseEntity.getBody().getData() : new ArrayList<>();
            });
    }

    public Mono<StudentResponse> getStudentByCode(String code) {
        return webClient.get()
            .uri("/student/code/{code}", code)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<ApiResponse<StudentResponse>>() {})
            .map(responseEntity -> responseEntity.getBody().getData());
    }
}
