package com.devtrack.chat.application.mapper.output;

import com.devtrack.chat.domain.entity.ChatMessage;
import com.devtrack.chat.infraestructure.controller.dto.output.ChatMessageOutputDto;
import com.devtrack.chat.infraestructure.controller.dto.output.RecentConversationOutputDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMessageOutputMapper {
    ChatMessageOutputDto entityToDto(ChatMessage chatMessage);
    RecentConversationOutputDto entityToRecentConversationDto(ChatMessage chatMessage);
}
