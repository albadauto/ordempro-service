package com.backend.ordempro.dto.customer;

import com.backend.ordempro.dto.tenant.TenantDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDTO {
    private boolean isSuccess;
    private Long id;
    private String name;
    private String email;
    private String whatsapp;
    private String address;
    private String number;
    private TenantDTO tenants;
}
