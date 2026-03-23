package com.backend.ordempro.repository;

import com.backend.ordempro.model.Customer;
import com.backend.ordempro.model.Tenants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c JOIN FETCH c.tenants WHERE c.tenants.id = :tenantId")
    List<Customer> findByTenantsId(Long tenantId);

    @Query("SELECT c FROM Customer c JOIN FETCH c.tenants WHERE c.tenants.id = :tenantId and c.id = :id")
    Customer findCustomerByIdAndTenantId(Long id, Long tenantId);


}
