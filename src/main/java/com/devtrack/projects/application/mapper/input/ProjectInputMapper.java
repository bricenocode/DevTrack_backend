package com.devtrack.projects.application.mapper.input;

import com.devtrack.projects.domain.entity.ProjectEntity;
import com.devtrack.projects.infraestructure.controller.dto.input.ProjectInputFullDto;
import com.devtrack.projects.infraestructure.controller.dto.input.ProjectInputSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProjectInputMapper {
    ProjectInputMapper projectInputMapper = Mappers.getMapper(ProjectInputMapper.class);

    ProjectEntity inputSimpleDtoToEntity(ProjectInputSimpleDto projectInputSimpleDto);
    ProjectEntity inputFullDtoToEntity(ProjectInputFullDto projectInputFullDto);

}
