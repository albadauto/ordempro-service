package com.backend.ordempro.controller;

import com.backend.ordempro.config.mapper.IPlansMapper;
import com.backend.ordempro.dto.plans.PlansResponseDTO;
import com.backend.ordempro.model.Plans;
import com.backend.ordempro.repository.PlansRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlansController {
    private final PlansRepository plansRepository;
    private final IPlansMapper plansMapper;
    public PlansController(PlansRepository plansRepository, IPlansMapper plansMapper){
        this.plansRepository = plansRepository;
        this.plansMapper = plansMapper;
    }
    @GetMapping("/get-all-plans")
    public ResponseEntity<List<PlansResponseDTO>> getAllPlans(){
        List<Plans> allPlans = this.plansRepository.findAll();
        if(allPlans.isEmpty())
            return ResponseEntity.notFound().build();
        List<PlansResponseDTO> responseDto = this.plansMapper.toDtoResponseList(allPlans);
        return ResponseEntity.ok().body(responseDto);
    }
}
