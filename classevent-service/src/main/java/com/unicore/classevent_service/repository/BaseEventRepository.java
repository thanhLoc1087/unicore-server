package com.unicore.classevent_service.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classevent_service.entity.BaseEvent;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.WeightType;

import reactor.core.publisher.Flux;

public interface BaseEventRepository extends ReactiveMongoRepository<BaseEvent, String> {
    Flux<BaseEvent> findAllByClassIdAndSubclassCodeAndType(String classId, String subclassCode, EventType type);

    @Query("{ 'classId': ?0, 'subclassCode': ?1, 'weightType': ?2, 'autocreated': { $exists: true, $eq: true } }")
    Flux<BaseEvent> findAllByClassIdAndSubclassCodeAndWeightTypeAndAutocreatedTrue(
        String classId, String subclassCode, WeightType weightType
    );
}
