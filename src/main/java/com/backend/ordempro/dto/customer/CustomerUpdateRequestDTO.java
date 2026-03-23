package com.backend.ordempro.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateRequestDTO extends CustomerRequestDTO{
    private Long id;
}
