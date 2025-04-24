package com.devtrack.users.application.mapper.input;

import com.example.User.domain.entity.UserEntity;
import com.example.User.infraestructure.controller.dto.input.UserInputLoginDto;
import com.example.User.infraestructure.controller.dto.input.UserInputSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserInputMapper {

    UserInputMapper userInputMapper = Mappers.getMapper(UserInputMapper.class);

    UserEntity inputSimpleDtoToEntity(UserInputSimpleDto userInputSimpleDto);
    UserEntity inputLoginDtoToEntity(UserInputLoginDto userInputLoginDto);

}
