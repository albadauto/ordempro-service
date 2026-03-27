package com.backend.ordempro.dto.serviceorder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceOrderResponseDTO {
    private String obs;
    private String orderNumber;
    private Long tenantId;
    private Long statusId;
    private Long customerId;
}
