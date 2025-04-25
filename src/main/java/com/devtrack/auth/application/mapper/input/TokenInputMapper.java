package com.devtrack.auth.application.mapper.input;

import com.devtrack.auth.domain.entity.TokenEntity;
import com.devtrack.auth.infraestructure.controller.dto.input.TokenInputSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TokenInputMapper {

    TokenInputMapper tokenInputMapper = Mappers.getMapper(TokenInputMapper.class);

    TokenEntity inputSimpleDtoToEntity(TokenInputSimpleDto tokenInputSimpleDto);
}
