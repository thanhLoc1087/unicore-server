package com.unicore.classevent_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classevent_service.dto.request.InternStudentListImportRequest;
import com.unicore.classevent_service.dto.request.InternStudentRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.entity.InternTopic;
import com.unicore.classevent_service.enums.ApiMessage;
import com.unicore.classevent_service.service.TopicService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/topic")
public class TopicController {
    private final TopicService topicService;

    @PostMapping("/intern/bulk")
    public Mono<ApiResponse<List<InternTopic>>> importInternBulk(@RequestBody InternStudentListImportRequest request) {
        return topicService.importInternTopics(request)
            .collectList()
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
    
    @PutMapping("/intern/{topicId}")
    public Mono<ApiResponse<InternTopic>> updateInternTopic(@PathVariable String topicId, @RequestBody InternStudentRequest request) {
        return topicService.updateInternTopic(topicId, request)
            .map(report -> new ApiResponse<>(
                report, 
                ApiMessage.SUCCESS.getMessage(), 
                HttpStatus.OK.value(),
                LocalDateTime.now()
            ));
    }
}
