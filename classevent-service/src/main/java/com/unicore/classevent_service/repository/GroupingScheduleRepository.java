package com.unicore.classevent_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.unicore.classevent_service.entity.GroupingSchedule;

public interface GroupingScheduleRepository extends ReactiveMongoRepository<GroupingSchedule, String> {
    
}
