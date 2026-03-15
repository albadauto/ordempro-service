package com.backend.ordempro.controller;

import com.backend.ordempro.dto.TenantDTO;
import com.backend.ordempro.model.Tenants;
import com.backend.ordempro.repository.TenantsRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final TenantsRepository tenantsRepository;

    public RegisterController(TenantsRepository tenantsRepository) {
        this.tenantsRepository = tenantsRepository;
    }

    @PostMapping("/tenant")
    public ResponseEntity<TenantDTO> CreateTenant(@RequestBody TenantDTO tenant) {
        Tenants tenants = new ModelMapper().map(tenant, Tenants.class);
        tenants.setPassword(BCrypt.hashpw(tenants.getPassword(), BCrypt.gensalt()));
        this.tenantsRepository.save(tenants);
        return ResponseEntity.ok(tenant);
    }

}
