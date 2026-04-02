package com.backend.ordempro.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
public class PremiumTenants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="TenantId")
    private Tenants tenants;
    private String stripeCustomerId;
    private LocalDate baseDate;
    private LocalDate dueDate;
}
