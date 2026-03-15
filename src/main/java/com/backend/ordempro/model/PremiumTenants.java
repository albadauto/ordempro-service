package com.backend.ordempro.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class PremiumTenants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="TenantId")
    private Tenants tenants;
    @OneToOne
    @JoinColumn(name="PlanId")
    private Plans plans;
}
