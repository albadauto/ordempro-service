package com.backend.ordempro.config.mapper;

import com.backend.ordempro.dto.plans.PlansResponseDTO;
import com.backend.ordempro.model.Plans;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPlansMapper {
    List<PlansResponseDTO> toDtoResponseList(List<Plans> plans);
}
