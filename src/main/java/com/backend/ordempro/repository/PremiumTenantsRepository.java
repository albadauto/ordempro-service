package com.backend.ordempro.repository;

import com.backend.ordempro.model.PremiumTenants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PremiumTenantsRepository extends JpaRepository<PremiumTenants, Long> {
    PremiumTenants findByStripeCustomerId(String customerId);
    Optional<PremiumTenants> findByTenantsId(Long tenantId);
}
