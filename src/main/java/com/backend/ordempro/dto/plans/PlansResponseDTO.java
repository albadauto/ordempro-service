package com.backend.ordempro.dto.plans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlansResponseDTO {
    private Long Id;
    private String Name;
    private String Price;
    private String StripePriceId;
}
