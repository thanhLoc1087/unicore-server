package com.unicore.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unicore.identity.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
