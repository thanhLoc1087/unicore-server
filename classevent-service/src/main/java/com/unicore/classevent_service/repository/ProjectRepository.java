package com.unicore.classevent_service.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classevent_service.entity.Project;

import reactor.core.publisher.Flux;

public interface ProjectRepository extends ReactiveCrudRepository<Project, String> {
    
    Flux<Project> findByClassIdAndSubclassCode(String classId, String subclassCode);


    @Query("{ 'class_id' : { $lte : ?0 }, 'subclass_code' : { $lte : ?1 }, 'start_date' : { $lte : ?2 } }")
    Flux<Project> findActiveProjects(String classId, String subclassCode, LocalDateTime todayStartDate);
}
