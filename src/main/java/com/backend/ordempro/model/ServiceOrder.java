package com.backend.ordempro.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String whatsapp;
    private String obs;
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name="tenantId")
    private Tenants tenants;

    @OneToOne
    @JoinColumn(name="statusId")
    private Status status;
}
