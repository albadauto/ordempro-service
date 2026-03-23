package com.backend.ordempro.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private final String KEY = "minha-chave-secreta-super-forte-123456";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }
    public String GenToken(String Email){
        return Jwts.builder()
                .setSubject(Email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey())
                .compact();
    }

    public String validateToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // ✅ ESSENCIAL
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
