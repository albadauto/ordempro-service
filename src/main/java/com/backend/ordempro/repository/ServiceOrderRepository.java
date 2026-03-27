package com.backend.ordempro.repository;

import com.backend.ordempro.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
   @Query("SELECT s FROM ServiceOrder s JOIN FETCH s.tenants WHERE s.orderNumber = :orderNumber and s.tenants.id = :tenantId")
   Optional<ServiceOrder> findByOrderNumberAndTenantId(String orderNumber, Long tenantId);
}
