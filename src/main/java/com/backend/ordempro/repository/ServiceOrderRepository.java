package com.backend.ordempro.repository;

import com.backend.ordempro.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
   @Query("SELECT s FROM ServiceOrder s JOIN FETCH s.tenants WHERE s.id = :id and s.tenants.id = :tenantId ORDER BY s.id desc")
   Optional<ServiceOrder> findByIdAndTenantId(Long id, Long tenantId);

   @Query("SELECT s FROM ServiceOrder s JOIN FETCH s.tenants WHERE s.tenants.id = :tenantId and s.status.id != 6")
   List<ServiceOrder> findAllByTenantId(Long tenantId);

   @Query("SELECT s FROM ServiceOrder s JOIN FETCH s.status WHERE s.id = :id and s.status.id != 6")
   Optional<ServiceOrder> findByIdAndNotFinish(Long id);
}
