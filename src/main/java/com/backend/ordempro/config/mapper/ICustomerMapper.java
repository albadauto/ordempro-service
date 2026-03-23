package com.backend.ordempro.config.mapper;

import com.backend.ordempro.dto.customer.CustomerRequestDTO;
import com.backend.ordempro.dto.customer.CustomerResponseDTO;
import com.backend.ordempro.dto.customer.CustomerUpdateRequestDTO;
import com.backend.ordempro.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {
    @Mapping(target = "tenants", ignore = true)
    Customer toEntity(CustomerRequestDTO dto);
    CustomerRequestDTO toDto(Customer customer);
    Customer toEntityUpdate(CustomerUpdateRequestDTO dto);
    CustomerResponseDTO toDtoResponse(Customer entity);
}
