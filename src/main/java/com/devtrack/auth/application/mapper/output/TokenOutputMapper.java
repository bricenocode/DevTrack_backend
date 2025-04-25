package com.devtrack.auth.application.mapper.output;

import com.devtrack.auth.domain.entity.TokenEntity;
import com.devtrack.auth.infraestructure.controller.dto.output.TokenOutputSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TokenOutputMapper {

    TokenOutputMapper tokenOutputMapper = Mappers.getMapper(TokenOutputMapper.class);

    TokenOutputSimpleDto entityToOutputSimpleDto(TokenEntity tokenEntity);
}
