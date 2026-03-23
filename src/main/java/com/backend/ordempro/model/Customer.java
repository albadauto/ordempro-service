package com.backend.ordempro.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String whatsapp;
    private String address;
    private String number;

    @ManyToOne
    @JoinColumn(name = "tenantid")
    private Tenants tenants;

}
