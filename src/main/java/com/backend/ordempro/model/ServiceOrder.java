package com.backend.ordempro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "service_order")
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String obs;
    private String orderNumber;
    private LocalDate insertDate;

    @ManyToOne
    @JoinColumn(name="tenantId")
    private Tenants tenants;

    @ManyToOne
    @JoinColumn(name="statusId")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}
