package com.unicore.classevent_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.classevent_service.entity.Council;

public interface CouncilRepository extends ReactiveCrudRepository<Council, String>{
    
}
