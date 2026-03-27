package com.backend.ordempro.config.mapper;

import com.backend.ordempro.dto.status.StatusRequestDTO;
import com.backend.ordempro.dto.status.StatusResponseDTO;
import com.backend.ordempro.model.Status;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IStatusMapper {
    List<StatusResponseDTO> toDtoResponse(List<Status> entity);
    StatusResponseDTO toDto(Status entity);
}
