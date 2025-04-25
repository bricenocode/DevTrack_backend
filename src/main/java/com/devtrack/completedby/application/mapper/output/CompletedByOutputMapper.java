package com.devtrack.completedby.application.mapper.output;

import com.devtrack.completedby.domain.entity.CompletedBy;
import com.devtrack.completedby.infraestructure.controller.dto.output.CompletedByOutputDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompletedByOutputMapper {
    CompletedByOutputDto outputDtoToEntity(CompletedBy completedBy);
}
