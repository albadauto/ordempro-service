package com.backend.ordempro.controller;

import com.backend.ordempro.dto.login.LoginRequestDTO;
import com.backend.ordempro.dto.login.LoginResponseDTO;
import com.backend.ordempro.model.Tenants;
import com.backend.ordempro.repository.TenantsRepository;
import com.backend.ordempro.utils.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    private final TenantsRepository tenantsRepository;
    public AuthController(TenantsRepository tenantsRepository){
        this.tenantsRepository = tenantsRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO dto){
        Optional<Tenants> tenant = this.tenantsRepository.findByEmail(dto.getEmail());
        if(tenant.isPresent()){
            Tenants tenantModel = tenant.get();
            if(BCrypt.checkpw(dto.getPassword(), tenantModel.getPassword())){
                String token = new JwtUtil().GenToken(tenantModel.getEmail());
                LoginResponseDTO response = new LoginResponseDTO();
                response.setToken(token);
                response.setIdTenant(tenant.get().getId());
                return ResponseEntity.ok(response);
            }else{
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();

    }
}
