package com.backend.ordempro.controller;

import com.backend.ordempro.dto.OtpResponseDTO;
import com.backend.ordempro.model.Otp;
import com.backend.ordempro.model.Tenants;
import com.backend.ordempro.repository.OtpRepository;
import com.backend.ordempro.repository.TenantsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
public class OtpController {

    private OtpRepository otpRepository;
    private TenantsRepository tenantsRepository;
    public OtpController(OtpRepository otpRepository, TenantsRepository tenantsRepository) {
        this.otpRepository = otpRepository;
        this.tenantsRepository = tenantsRepository;
    }
    @PostMapping("/send-otp")
    public ResponseEntity<OtpResponseDTO> sendOtp(Long idTenant){

        var otpCode = genOtp();

        Tenants tenant = tenantsRepository.findById(idTenant)
                .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));

        Otp otp = new Otp();
        otp.setCode(otpCode);
        otp.setTenant(tenant);
        otpRepository.save(otp);
        return ResponseEntity.ok(new OtpResponseDTO(otpCode, idTenant));
    }

    private static String genOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10)); // Gera um número de 0-9
        }

        return sb.toString();
    }
}
