package com.backend.ordempro.config;

import com.backend.ordempro.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private CorsConfigurationSource corsConfigurationSource; // 👈 injeta o bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/authenticate").permitAll()
                        .requestMatchers("/admin/**").authenticated()
                        .requestMatchers("/customer/**").authenticated()
                        .requestMatchers("/serviceorder/**").authenticated()
                        .requestMatchers("/status/**").authenticated()
                        .anyRequest().permitAll()

                ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        ;

        return http.build();
    }
}