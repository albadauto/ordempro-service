package com.backend.ordempro.repository;

import com.backend.ordempro.model.Tenants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantsRepository extends JpaRepository<Tenants, Long> {
}
