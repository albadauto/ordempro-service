package com.backend.ordempro.dto.login;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class LoginRequestDTO {
    private String email;
    private String password;
}
