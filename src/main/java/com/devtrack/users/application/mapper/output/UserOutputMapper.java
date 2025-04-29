package com.devtrack.users.application.mapper.output;

import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputLoginDto;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserOutputMapper {

    UserOutputMapper userOutputMapper = Mappers.getMapper(UserOutputMapper.class);

    UserOutputSimpleDto entityToOutputSimpleDto(UserEntity entity);
    UserOutputLoginDto entityToOutputLoginDto(UserEntity entity);
    List<UserOutputSimpleDto> listEntityToOutputSimpleDto(List<UserEntity> entities);
}
