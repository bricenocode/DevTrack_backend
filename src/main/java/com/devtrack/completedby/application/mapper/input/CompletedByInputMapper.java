package com.devtrack.completedby.application.mapper.input;

import com.devtrack.completedby.domain.entity.CompletedBy;
import com.devtrack.completedby.infraestructure.controller.dto.input.CompletedByInputDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompletedByInputMapper {
    CompletedBy inputDtoToEntity(CompletedByInputDto completedByInputDto);
}
