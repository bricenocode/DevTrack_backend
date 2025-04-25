package com.devtrack.tasks.application.mapper.input;

import com.devtrack.tasks.domain.entity.TaskEntity;
import com.devtrack.tasks.infraestructure.controller.dto.input.TaskInputSimpleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskInputMapper {
    TaskEntity inputSimpleDtoToEntity(TaskInputSimpleDto taskInputSimpleDto);
}
