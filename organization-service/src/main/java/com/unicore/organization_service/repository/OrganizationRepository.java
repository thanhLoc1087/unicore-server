package com.unicore.organization_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.unicore.organization_service.entity.Organization;

public interface OrganizationRepository extends ReactiveCrudRepository<Organization, String> {}
