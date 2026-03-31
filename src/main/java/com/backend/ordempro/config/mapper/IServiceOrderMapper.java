package com.backend.ordempro.config.mapper;

import com.backend.ordempro.dto.customer.CustomerRequestDTO;
import com.backend.ordempro.dto.serviceorder.ServiceOrderRequestDTO;
import com.backend.ordempro.dto.serviceorder.ServiceOrderResponseDTO;
import com.backend.ordempro.dto.serviceorder.ServiceOrderUpdateRequestDTO;
import com.backend.ordempro.model.Customer;
import com.backend.ordempro.model.ServiceOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IServiceOrderMapper {
    @Mapping(target = "tenants", ignore = true)
    ServiceOrder toEntity(ServiceOrderRequestDTO dto);
    ServiceOrderRequestDTO toDto(ServiceOrder dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "orderNumber", source = "orderNumber")
    @Mapping(target = "tenantId", source = "tenants.id")
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "statusId", source = "status.id")
    @Mapping(target = "tenant", source = "tenants")
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "status", source = "status")
    ServiceOrderResponseDTO toDtoResponse(ServiceOrder entity);
    List<ServiceOrderResponseDTO> toDtoResponseList(List<ServiceOrder> entity);

    ServiceOrder toDtoRequestUpdate(ServiceOrderUpdateRequestDTO dto);
}
