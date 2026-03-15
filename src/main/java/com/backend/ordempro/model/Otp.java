package com.backend.ordempro.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    @ManyToOne
    @JoinColumn(name = "tenantId")
    private Tenants tenant;
}
