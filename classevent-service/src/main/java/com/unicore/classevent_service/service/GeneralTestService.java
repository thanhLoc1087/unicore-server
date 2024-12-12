package com.unicore.classevent_service.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.GeneralTestBulkCreationRequest;
import com.unicore.classevent_service.dto.request.GeneralTestUpdateRequest;
import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.response.BaseEventResponse;
import com.unicore.classevent_service.dto.response.GeneralTestResponse;
import com.unicore.classevent_service.entity.GeneralTest;
import com.unicore.classevent_service.entity.Project;
import com.unicore.classevent_service.enums.ExamFormat;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.mapper.GeneralTestMapper;
import com.unicore.classevent_service.repository.GeneralTestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneralTestService {
    private final GeneralTestRepository repository;
    private final GeneralTestMapper mapper;

    private final ProjectService projectService;

    /// Tạo bulk
    public Flux<BaseEventResponse> createBulk(GeneralTestBulkCreationRequest request) {
        return Flux.fromIterable(request.getRequests())
            .flatMap(item -> {
                if (item.getFormat().isProject()) {
                    log.info("Project create");
                    Project project = Project.builder()    
                        .classId(item.getClassId())
                        .subclassCode(item.getSubclassCode())
                        .name("Đồ án")
                        .description("Đồ án")
                        .inGroup(true)
                        .weightType(item.getWeightType())
                        .weight(100.0f)
                        .allowGradeReview(true)
                        .reviewTimes(3)
                        .allowTopicSuggestion(ExamFormat.DO_AN_TOT_NGHIEP.equals(item.getFormat()))
                        .build();
                    return projectService.saveProject(project);
                } else {
                    log.info("TEst create");
                    GeneralTest test = GeneralTest.builder()
                        .classId(item.getClassId())
                        .subclassCode(item.getSubclassCode())
                        .weightType(item.getWeightType())
                        .createdBy("LOC")
                        .createdDate(Date.from(Instant.now()))
                        .allowGradeReview(true)
                        .inGroup(false)
                        .reviewTimes(3)
                        .build();
                    
                    return repository.save(test);
                }
            })
            .map(response -> {
                if (response instanceof GeneralTest test)
                    return mapper.toResponse(test);
                else 
                    return (BaseEventResponse) response;
            });
    }

    /// Edit
    public Mono<GeneralTestResponse> editTest(String id, GeneralTestUpdateRequest request) {
        return repository.findById(id)
            .map(test -> {
                test.setPlace(request.getPlace());
                test.setTime(request.getTime());
                test.setName(request.getName());
                test.setDescription(request.getDescription());
                test.setAllowGradeReview(request.isAllowGradeReview());
                test.setReviewTimes(request.getReviewTimes());
                test.setModifiedBy("LOC");
                test.setModifiedDate(Date.from(Instant.now()));
                return test;
            })
            .flatMap(repository::save)
            .map(mapper::toResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    /// Lấy theo lớp
    public Flux<GeneralTestResponse> getByClass(GetByClassRequest request) {
        return repository.findAllByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(mapper::toResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<GeneralTestResponse> getById(String id) {
        log.info("Get test with id: " + id);
        return repository.findById(id)
            .map(mapper::toResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
} 
