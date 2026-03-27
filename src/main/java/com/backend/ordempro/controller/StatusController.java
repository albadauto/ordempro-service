package com.backend.ordempro.controller;

import com.backend.ordempro.config.mapper.IStatusMapper;
import com.backend.ordempro.dto.status.StatusResponseDTO;
import com.backend.ordempro.model.Status;
import com.backend.ordempro.repository.StatusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {
    private final IStatusMapper statusMapper;
    private final StatusRepository statusRepository;
    public StatusController(IStatusMapper statusMapper, StatusRepository statusRepository){
        this.statusMapper = statusMapper;
        this.statusRepository = statusRepository;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<StatusResponseDTO>> getAll(){
        List<Status> status = this.statusRepository.findAll();
        if(!status.isEmpty()){
            List<StatusResponseDTO> listStatusDto = this.statusMapper.toDtoResponse(status);
            return ResponseEntity.ok().body(listStatusDto);
        }
        return ResponseEntity.notFound().build();
    }
}
