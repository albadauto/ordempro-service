package com.backend.ordempro.dto.serviceorder;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ServiceOrderRequestFilterDTO {
    private LocalDate insertDate;
    private Long tenantId;
}
