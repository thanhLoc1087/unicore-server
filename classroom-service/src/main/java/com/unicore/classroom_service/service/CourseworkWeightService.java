package com.unicore.classroom_service.service;

import org.springframework.stereotype.Service;

import com.unicore.classroom_service.dto.request.CourseworkWeightCreationRequest;
import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.response.CourseworkWeightResponse;
import com.unicore.classroom_service.entity.CourseworkWeight;
import com.unicore.classroom_service.entity.CourseworkWeightDetail;
import com.unicore.classroom_service.exception.AppException;
import com.unicore.classroom_service.exception.ErrorCode;
import com.unicore.classroom_service.mapper.CourseworkWeightMapper;
import com.unicore.classroom_service.repository.CourseworkWeightRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CourseworkWeightService {
    private final CourseworkWeightRepository courseworkWeightRepository;
    private final CourseworkWeightMapper courseworkWeightMapper;

    public Mono<CourseworkWeightResponse> createCourseworkWeight(CourseworkWeightCreationRequest request) {
        return checkDuplicate(request.getClassId(), request.getSubclassCode())
            .flatMap(result -> Boolean.TRUE.equals(result) ? 
                Mono.error(new AppException(ErrorCode.DUPLICATE)) : 
                saveCourseworkWeight(request));
    }

    public Mono<CourseworkWeightResponse> saveCourseworkWeight(CourseworkWeightCreationRequest request) {
        return Mono.just(request)
        .map(weight -> {
            CourseworkWeight result = courseworkWeightMapper.toCourseworkWeight(weight);
            for (CourseworkWeightDetail detail : result.getDetails()) {
                detail.setCode(generateCode(detail.getName(), detail.getValue()));
            }
            return result;
        })
        .flatMap(courseworkWeightRepository::save)
        .map(courseworkWeightMapper::toCourseworkWeightResponse);
    }

    public Mono<CourseworkWeightResponse> getCourseworkWeightById(String id) {
        return courseworkWeightRepository.findById(id)
            .map(courseworkWeightMapper::toCourseworkWeightResponse);
    }

    public Mono<CourseworkWeightResponse> getCourseworkWeightByClass(GetByClassRequest request) {
        return courseworkWeightRepository.findByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(courseworkWeightMapper::toCourseworkWeightResponse);
    }

    private Mono<Boolean> checkDuplicate(String classId, String subclassCode) {
        return courseworkWeightRepository.findByClassIdAndSubclassCode(classId, subclassCode)
            .hasElement();
    }

    private String generateCode(String name, int value) {
        return name.length() >= 3 ? 
            name.substring(0, 3).toUpperCase() + value : 
            name.toUpperCase() + value;
    }
}
