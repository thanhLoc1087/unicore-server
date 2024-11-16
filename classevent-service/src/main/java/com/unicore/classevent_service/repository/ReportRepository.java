package com.unicore.classevent_service.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classevent_service.entity.Report;

import reactor.core.publisher.Flux;

public interface ReportRepository extends ReactiveCrudRepository<Report, String>{
    Flux<Report> findByClassIdAndSubclassCode(String classId, String subclassCode);


    @Query("{ 'class_id' : { $lte : ?0 }, 'subclass_code' : { $lte : ?1 }, 'publishDate' : { $lte : ?2 }, 'endDate' : { $gte : ?3 } }")
    Flux<Report> findActiveReports(String classId, String subclassCode, LocalDateTime todayStartDate, LocalDateTime todayEndDate);
    
}
