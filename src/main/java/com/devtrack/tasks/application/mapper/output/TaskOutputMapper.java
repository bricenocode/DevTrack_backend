package com.devtrack.tasks.application.mapper.output;

import com.devtrack.tasks.domain.entity.TaskEntity;
import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputFullDto;
import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskOutputMapper {

    TaskOutputMapper taskOutputMapper = Mappers.getMapper(TaskOutputMapper.class);

    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    TaskOutputSimpleDto taskOutputSimpleDtoToEntity(TaskEntity taskEntity);
    TaskOutputFullDto taskOutputFullDtoToEntity(TaskEntity taskEntity);

}
