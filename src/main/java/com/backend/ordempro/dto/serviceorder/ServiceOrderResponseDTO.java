package com.backend.ordempro.dto.serviceorder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceOrderResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String whatsapp;
    private String obs;
    private String orderNumber;
    private Long tenantId;
}
