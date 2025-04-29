package com.devtrack.auth.application.mapper.input;

import com.devtrack.auth.domain.entity.TokenEntity;
import com.devtrack.auth.infraestructure.controller.dto.input.TokenInputProfileDto;
import com.devtrack.auth.infraestructure.controller.dto.input.TokenInputSimpleDto;
import com.devtrack.users.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TokenInputMapper {
    TokenEntity inputSimpleDtoToEntity(TokenInputSimpleDto tokenInputSimpleDto);
    UserEntity inputProfileDtoToUserEntity(TokenInputProfileDto tokenInputProfileDto);
}
