package com.backend.ordempro.controller;

import com.backend.ordempro.config.mapper.IServiceOrderMapper;
import com.backend.ordempro.dto.api.ApiResponseDTO;
import com.backend.ordempro.dto.serviceorder.ServiceOrderRequestDTO;
import com.backend.ordempro.dto.serviceorder.ServiceOrderResponseDTO;
import com.backend.ordempro.model.Customer;
import com.backend.ordempro.model.ServiceOrder;
import com.backend.ordempro.model.Status;
import com.backend.ordempro.model.Tenants;
import com.backend.ordempro.repository.CustomerRepository;
import com.backend.ordempro.repository.ServiceOrderRepository;
import com.backend.ordempro.repository.StatusRepository;
import com.backend.ordempro.repository.TenantsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/serviceorder")
public class ServiceOrderController {
    private final IServiceOrderMapper serviceOrderMapper;
    private final ServiceOrderRepository serviceOrderRepository;
    private final CustomerRepository customerRepository;
    private final StatusRepository statusRepository;
    private final TenantsRepository tenantsRepository;

    public ServiceOrderController(IServiceOrderMapper serviceOrderMapper,
                                  ServiceOrderRepository serviceOrderRepository,
                                  StatusRepository statusRepository,
                                  TenantsRepository tenantsRepository,
                                  CustomerRepository customerRepository){
        this.serviceOrderMapper = serviceOrderMapper;
        this.serviceOrderRepository = serviceOrderRepository;
        this.statusRepository = statusRepository;
        this.tenantsRepository = tenantsRepository;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/create-order")
    public ResponseEntity<ApiResponseDTO<String>> createOrder(@RequestBody ServiceOrderRequestDTO dto){
        ServiceOrder entity = this.serviceOrderMapper.toEntity(dto);
        entity.setOrderNumber(this.generateRandomOSNumber());
        Optional<Status> status = this.statusRepository.findById(dto.getStatusId());
        Optional<Tenants> tenants = this.tenantsRepository.findById(dto.getTenantId());
        Optional<Customer> customer = this.customerRepository.findById(dto.getCustomerId());
        status.ifPresent(entity::setStatus);
        tenants.ifPresent(entity::setTenants);
        customer.ifPresent(entity::setCustomer);
        ServiceOrder orderSaved = this.serviceOrderRepository.save(entity);
        ApiResponseDTO<String> response = new ApiResponseDTO<String>();
        response.setSuccess(true);
        response.setData(orderSaved.getOrderNumber());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get-service-order/{orderNumber}/{tenantId}")
    public ResponseEntity<ServiceOrderResponseDTO> getServiceOrderByOrderNumber(@PathVariable String orderNumber, @PathVariable Long tenantId){
        Optional<ServiceOrder> serviceOrder = this.serviceOrderRepository.findByOrderNumberAndTenantId(orderNumber, tenantId);
        if(serviceOrder.isPresent()){
            ServiceOrderResponseDTO response = this.serviceOrderMapper.toDtoResponse(serviceOrder.get());
            return ResponseEntity.ok().body(response);
        }
         return ResponseEntity.notFound().build();
    }

    @GetMapping("/get-all-os/{tenantId}")
    public ResponseEntity<List<ServiceOrderResponseDTO>> getAllServiceOrder(@PathVariable Long tenantId){
        List<ServiceOrder> servicesOrder = this.serviceOrderRepository.findAllByTenantId(tenantId);
        if(!servicesOrder.isEmpty()){
            List<ServiceOrderResponseDTO> responseDto = this.serviceOrderMapper.toDtoResponseList(servicesOrder);
            return ResponseEntity.ok().body(responseDto);
        }

        return ResponseEntity.notFound().build();
    }

    private String generateRandomOSNumber(){
        String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 10;
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * charPool.length());
            randomString.append(charPool.charAt(index));
        }

        return String.format("OS-%s", randomString);
    }
}
