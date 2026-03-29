package com.backend.ordempro.dto.serviceorder;

import com.backend.ordempro.dto.customer.CustomerResponseDTO;
import com.backend.ordempro.dto.status.StatusResponseDTO;
import com.backend.ordempro.dto.tenant.TenantDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceOrderResponseDTO {
    private String obs;
    private String orderNumber;
    private TenantDTO tenant;
    private CustomerResponseDTO customer;
    private StatusResponseDTO status;
}
