package com.unicore.classroom_service.repository;

import com.unicore.classroom_service.dto.request.ClassFilterRequest;
import com.unicore.classroom_service.entity.Classroom;

import reactor.core.publisher.Flux;

public interface CustomClassroomRepository {
    Flux<Classroom> findByFilters(ClassFilterRequest filterRequest);
}
