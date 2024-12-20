package com.unicore.profile_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.profile_service.entity.Staff;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface StaffRepository extends ReactiveCrudRepository<Staff, String> {
        Flux<Staff> findByOrganizationId(String organizationId);   
        Mono<Staff> findByCode(String code);
}
