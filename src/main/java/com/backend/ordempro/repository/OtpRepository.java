package com.backend.ordempro.repository;

import com.backend.ordempro.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OtpRepository extends JpaRepository<Otp, Long> {
}
