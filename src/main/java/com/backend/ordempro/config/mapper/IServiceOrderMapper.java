package com.backend.ordempro.config.mapper;

import com.backend.ordempro.dto.customer.CustomerRequestDTO;
import com.backend.ordempro.dto.serviceorder.ServiceOrderRequestDTO;
import com.backend.ordempro.dto.serviceorder.ServiceOrderResponseDTO;
import com.backend.ordempro.model.Customer;
import com.backend.ordempro.model.ServiceOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IServiceOrderMapper {
    @Mapping(target = "tenants", ignore = true)
    ServiceOrder toEntity(ServiceOrderRequestDTO dto);
    ServiceOrderRequestDTO toDto(ServiceOrder dto);
    ServiceOrderResponseDTO toDtoResponse(ServiceOrder entity);
}
