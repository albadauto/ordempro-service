package com.backend.ordempro.controller;

import com.backend.ordempro.dto.otp.OtpResponseDTO;
import com.backend.ordempro.model.Otp;
import com.backend.ordempro.model.Tenants;
import com.backend.ordempro.repository.OtpRepository;
import com.backend.ordempro.repository.TenantsRepository;
import com.backend.ordempro.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;

@RestController
public class OtpController {

    private final OtpRepository otpRepository;
    private final TenantsRepository tenantsRepository;
    @Autowired
    private EmailService emailService;

    public OtpController(OtpRepository otpRepository, TenantsRepository tenantsRepository) {
        this.otpRepository = otpRepository;
        this.tenantsRepository = tenantsRepository;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<OtpResponseDTO> sendOtp(Long idTenant) {
        String otpCode = this.generateOtp(idTenant);
        return ResponseEntity.ok(new OtpResponseDTO(otpCode));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<OtpResponseDTO> verifyOtp(@RequestBody OtpResponseDTO dto){
        var otp = this.otpRepository.findByCodeAndTenantId(dto.getCode(), dto.getTenantId()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.otpRepository.deleteById(otp.getId());
        return ResponseEntity.ok(new OtpResponseDTO());
    }

    private static String genOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10)); // Gera um número de 0-9
        }
        return sb.toString();
    }

    private String generateOtp(Long idTenant) {
        var otpCode = genOtp();

        Tenants tenant = this.tenantsRepository.findById(idTenant)
                .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));
        Otp otp = new Otp();
        otp.setCode(otpCode);
        otp.setTenant(tenant);
        otpRepository.save(otp);

        this.emailService.sendEmail(tenant.getEmail(), "Ordem PRO - Codigo OTP", otp.getCode());
        return otpCode;
    }


}
