package com.backend.ordempro.repository;

import com.backend.ordempro.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
   Optional<ServiceOrder> findByOrderNumber(String orderNumber);
}
