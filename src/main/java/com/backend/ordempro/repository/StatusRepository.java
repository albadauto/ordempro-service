package com.backend.ordempro.repository;

import com.backend.ordempro.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
