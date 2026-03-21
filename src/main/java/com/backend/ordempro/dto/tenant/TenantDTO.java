package com.backend.ordempro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantDTO {
    private String name;
    private String email;
    private String password;
    private String cnpj;
    private String whatsapp;
}
