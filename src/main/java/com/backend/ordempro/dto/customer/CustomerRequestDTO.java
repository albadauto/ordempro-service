package com.backend.ordempro.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDTO {
    private String name;
    private String email;
    private String whatsapp;
    private String address;
    private String number;
    private Long tenantId;
}
