package com.backend.ordempro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping("/check-health")
    public ResponseEntity<String> CheckHealth(){
        return ResponseEntity.ok().body("Api is running!");
    }
}
