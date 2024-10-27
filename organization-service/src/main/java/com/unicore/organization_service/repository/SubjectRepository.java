package com.unicore.organization_service.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.organization_service.entity.Subject;

public interface SubjectRepository extends ReactiveCrudRepository<Subject, String>{
    
}
