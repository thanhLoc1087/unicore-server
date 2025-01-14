package com.unicore.classevent_service.repository.httpclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetClassBySemesterAndYearRequest;
import com.unicore.classevent_service.dto.request.UpdateClassGroupingRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.ClassroomResponse;
import com.unicore.classevent_service.dto.response.EventGroupingResponse;

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

    public Mono<ApiResponse<EventGroupingResponse>> getClassGroup(GetByClassRequest request) {
        return webClient.post()
            .uri("/grouping/get") // Adjust the base URL as needed
            .contentType(MediaType.APPLICATION_JSON) // Specify the content type
            .bodyValue(request) // Serialize the request body to JSON
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<EventGroupingResponse>>() {});
        }
        
    public Mono<ApiResponse<ClassroomResponse>> getClassroomByCode(GetClassBySemesterAndYearRequest request) {
        return webClient.post()
            .uri("/code") // Adjust the base URL as needed
            .contentType(MediaType.APPLICATION_JSON) // Specify the content type
            .bodyValue(request) // Serialize the request body to JSON
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<ClassroomResponse>>() {});
    }

    
    public Mono<ClassroomResponse> getClassById(String classId) {
        return webClient.get()
            .uri("/{classId}", classId)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<ApiResponse<ClassroomResponse>>() {})
            // .bodyToMono(new ParameterizedTypeReference<ApiResponse<SubjectResponse>>() {})
            .map(responseEntity -> responseEntity.getBody().getData());
    }
        
    public Mono<ApiResponse<ClassroomResponse>> updateClassroom(UpdateClassGroupingRequest request) {
        return webClient.put()
            .uri("/grouping") // Adjust the base URL as needed
            .contentType(MediaType.APPLICATION_JSON) // Specify the content type
            .bodyValue(request) // Serialize the request body to JSON
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<ClassroomResponse>>() {});
    }
}
