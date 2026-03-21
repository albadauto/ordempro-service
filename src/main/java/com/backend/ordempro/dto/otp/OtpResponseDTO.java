package com.backend.ordempro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpResponseDTO {
    public OtpResponseDTO() {}
    public OtpResponseDTO(String code){
        this.code = code;
    }
    private String code;
    private Long tenantId;
}
