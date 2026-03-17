package com.backend.ordempro.repository;

import com.backend.ordempro.model.Tenants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantsRepository extends JpaRepository<Tenants, Long> {
    Optional<Tenants> findByEmail(String email);
}
