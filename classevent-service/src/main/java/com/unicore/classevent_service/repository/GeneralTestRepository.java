package com.unicore.classevent_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classevent_service.entity.GeneralTest;
import com.unicore.classevent_service.enums.WeightType;

import reactor.core.publisher.Flux;

public interface GeneralTestRepository extends ReactiveCrudRepository<GeneralTest, String> {
    Flux<GeneralTest> findAllByClassIdAndSubclassCode(String classId, String subclassCode);

    Flux<GeneralTest> findAllByClassIdAndSubclassCodeAndWeightTypeAndAutocreatedTrue(
        String classId, String subclassCode, WeightType weightType
    );
}
