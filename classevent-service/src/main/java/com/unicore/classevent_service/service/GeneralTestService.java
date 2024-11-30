package com.unicore.classevent_service.service;

import java.util.Date;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.GeneralTestBulkCreationRequest;
import com.unicore.classevent_service.dto.request.GeneralTestUpdateRequest;
import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.response.GeneralTestResponse;
import com.unicore.classevent_service.entity.GeneralTest;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.mapper.GeneralTestMapper;
import com.unicore.classevent_service.repository.GeneralTestRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GeneralTestService {
    private final GeneralTestRepository repository;
    private final GeneralTestMapper mapper;

    /// Tạo bulk
    public Flux<GeneralTestResponse> createBulk(GeneralTestBulkCreationRequest request) {
        return Flux.fromIterable(request.getRequests())
            .map(item ->  GeneralTest.builder()
                .classId(item.getClassId())
                .subclassCode(item.getSubclassCode())
                .weightType(item.getWeightType())
                .createdBy("LOC")
                .createdDate(Date.from(Instant.now()))
                .allowGradeReview(true)
                .inGroup(false)
                .reviewTimes(3)
                .build()
            )
            .flatMap(repository::save)
            .map(mapper::toResponse);
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
        return repository.findById(id)
            .map(mapper::toResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
} 
