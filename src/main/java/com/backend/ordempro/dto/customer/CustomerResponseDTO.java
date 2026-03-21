package com.backend.ordempro.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDTO {
    private String name;
    private String email;
    private String whatsapp;
    private String address;
    private String number;
}
