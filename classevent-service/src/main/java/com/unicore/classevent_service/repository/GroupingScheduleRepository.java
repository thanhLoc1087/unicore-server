package com.unicore.classevent_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classevent_service.entity.GroupingSchedule;

import reactor.core.publisher.Mono;

public interface GroupingScheduleRepository extends ReactiveMongoRepository<GroupingSchedule, String> {
    Mono<GroupingSchedule> findByClassIdAndSubclassCodeAndIsDefaultTrue(String classId, String subclassCode);
}
