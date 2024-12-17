package com.unicore.classevent_service.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classevent_service.entity.BaseEvent;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.WeightType;

import reactor.core.publisher.Flux;

public interface BaseEventRepository extends ReactiveMongoRepository<BaseEvent, String> {
    Flux<BaseEvent> findAllByClassIdAndSubclassCodeAndType(String classId, String subclassCode, EventType type);

    // Test, Project
    @Query("{ 'classId': ?0, 'subclassCode': ?1, 'weightType': ?2, 'autocreated': { $exists: true, $eq: true } }")
    Flux<BaseEvent> findAllByClassIdAndSubclassCodeAndWeightTypeAndAutocreatedTrue(
        String classId, String subclassCode, WeightType weightType
        );
        
    @Query("{ 'project_id': { $exists: true, $eq: ?0 } }")
    Flux<BaseEvent> findAllByProjectIdAndType(String projectId, EventType type);

    @Query("{ " +
    "  'class_id': { $lte: ?0 }, " +
    "  'subclass_code': { $lte: ?1 }, " +
    "  'publish_date': { $exists: true, $lte: ?2 }, " +
    "  'date': { $exists: true, $lte: ?2, $gte: ?3 }, " +
    "  'end_date': { $exists: true, $gte: ?3 }, " +
    "  'type': { $eq: ?4 } " +
    "}")
    Flux<BaseEvent> findActiveEvents(
        String classId,
        String subclassCode,
        LocalDateTime todayStartDate,
        LocalDateTime todayEndDate,
        EventType type
    );
}
