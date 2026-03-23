package com.backend.ordempro.controller;

import com.backend.ordempro.config.mapper.ICustomerMapper;
import com.backend.ordempro.dto.customer.CustomerRequestDTO;
import com.backend.ordempro.dto.customer.CustomerResponseDTO;
import com.backend.ordempro.dto.customer.CustomerUpdateRequestDTO;
import com.backend.ordempro.model.Customer;
import com.backend.ordempro.model.Tenants;
import com.backend.ordempro.repository.CustomerRepository;
import com.backend.ordempro.repository.TenantsRepository;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final TenantsRepository tenantsRepository;
    private final ICustomerMapper customerMapper;
    public CustomerController(CustomerRepository customerRepository, TenantsRepository tenantsRepository, ICustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.tenantsRepository = tenantsRepository;
        this.customerMapper = customerMapper;
    }

    @PostMapping("/create-customer")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO dto){
        Customer customerEntity = this.customerMapper.toEntity(dto);
        Optional<Tenants> tenant = this.tenantsRepository.findById(dto.getTenantId());
        tenant.ifPresent(customerEntity::setTenants);
        customerRepository.save(customerEntity);
        CustomerResponseDTO response = new CustomerResponseDTO();
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-customers/{tenantId}")
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(@PathVariable Long tenantId){
        List<Customer> customers = this.customerRepository.findByTenantsId(tenantId);
        if(!customers.isEmpty()){
            ModelMapper modelMapper = new ModelMapper();
            List<CustomerResponseDTO> dtoList = customers.stream()
                    .map(customer -> modelMapper.map(customer, CustomerResponseDTO.class))
                    .toList();
            return ResponseEntity.ok().body(dtoList);
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete-customer/{id}")
    public ResponseEntity<CustomerResponseDTO> deleteCustomer(@PathVariable Long id){
        this.customerRepository.deleteById(id);
        CustomerResponseDTO response = new CustomerResponseDTO();
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get-customer/{id}/{tenantId}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable Long id, @PathVariable Long tenantId){
       Customer customer = this.customerRepository.findCustomerByIdAndTenantId(id, tenantId);
       if(customer != null){
           CustomerResponseDTO response = this.customerMapper.toDtoResponse(customer);
           return ResponseEntity.ok().body(response);
       }
       return ResponseEntity.notFound().build();
    }

    @PutMapping("/update-customer/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateRequestDTO dto){
        Customer customerFind = this.customerRepository.findCustomerByIdAndTenantId(id, dto.getTenantId());
        if(customerFind != null){
            Customer customerUpdate = this.customerMapper.toEntity(dto);
            customerUpdate.setId(id);
            Optional<Tenants> tenantFind = this.tenantsRepository.findById(dto.getTenantId());
            tenantFind.ifPresent(customerUpdate::setTenants);
            this.customerRepository.save(customerUpdate);
            CustomerResponseDTO response = new CustomerResponseDTO();
            response.setSuccess(true);
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.notFound().build();

    }

}
