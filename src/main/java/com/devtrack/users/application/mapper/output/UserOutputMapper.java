package com.devtrack.users.application.mapper.output;

import com.example.User.domain.entity.UserEntity;
import com.example.User.infraestructure.controller.dto.output.UserOutputLoginDto;
import com.example.User.infraestructure.controller.dto.output.UserOutputSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserOutputMapper {

    UserOutputMapper userOutputMapper = Mappers.getMapper(UserOutputMapper.class);

    UserOutputSimpleDto entityToOutputSimpleDto(UserEntity entity);
    UserOutputLoginDto entityToOutputLoginDto(UserEntity entity);
}
