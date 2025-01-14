package com.unicore.classevent_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classevent_service.entity.BaseEvent;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.WeightType;

import reactor.core.publisher.Flux;


public interface BaseEventRepository extends ReactiveMongoRepository<BaseEvent, String> {
    
    Flux<BaseEvent> findAllByClassId(String classId);

    Flux<BaseEvent> findAllByClassIdAndSubclassCode(String classId, String subclassCode);

    Flux<BaseEvent> findAllByClassIdAndWeightType(String classId, WeightType weightType);

    Flux<BaseEvent> findAllByClassIdAndSubclassCodeAndType(String classId, String subclassCode, EventType type);


    @Query("{ 'classId': ?0, 'subclassCode': ?1, 'type': 'REPORT', 'projectId': { $exists: true, $eq: '' } }")
    Flux<BaseEvent> findAllReportsByClassIdAndSubclassCode(String classId, String subclassCode);

    // Test, Project
    @Query("{ 'classId': ?0, 'subclassCode': ?1, 'weightType': ?2, 'autocreated': { $exists: true, $eq: true } }")
    Flux<BaseEvent> findAllByClassIdAndSubclassCodeAndWeightTypeAndAutocreatedTrue(
        String classId, String subclassCode, WeightType weightType
        );
        
    @Query("{ 'projectId': { $exists: true, $eq: ?0 }, 'type': ?1 }")
    Flux<BaseEvent> findAllByProjectIdAndType(String projectId, EventType type);

    @Query("{ " +
    "  'classId': { $lte: ?0 }, " +
    "  'subclassCode': { $lte: ?1 }, " +
    "  'publishDate': { $exists: true, $lte: ?2 }, " +
    "  'closeSubmissionDate': { $exists: true, $gte: ?3 }, " +
    "  'type': { $eq: ?4 } " +
    "}")
    Flux<BaseEvent> findActiveEvents(
        String classId,
        String subclassCode,
        LocalDateTime todayStartDate,
        LocalDateTime todayEndDate,
        EventType type
    );

    @Query("{ 'type': 'PROJECT', 'topics.id': { $in: ?0 }, 'topics': { $exists: true } }")
    Flux<BaseEvent> findProjectsByTopicIds(List<String> topicIds);
}
