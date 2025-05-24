package com.devtrack.chat.application.mapper.input;

import com.devtrack.chat.domain.entity.ChatMessage;
import com.devtrack.chat.infraestructure.controller.dto.input.ChatMessageInputDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMessageInputMapper {
     ChatMessage inputDtoToEntity(ChatMessageInputDto chatMessageInputDto);
}
