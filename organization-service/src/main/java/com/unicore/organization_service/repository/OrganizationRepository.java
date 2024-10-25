package com.unicore.organization_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicore.organization_service.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {}
