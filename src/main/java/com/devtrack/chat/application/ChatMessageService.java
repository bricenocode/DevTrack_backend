package com.devtrack.chat.application;

import com.devtrack.chat.domain.entity.ChatMessage;
import com.devtrack.chat.infraestructure.controller.dto.output.RecentConversationOutputDto;

import java.util.List;

public interface ChatMessageService {
    ChatMessage sendMessage(ChatMessage chatMessage);
    List<ChatMessage> getChatHistory(String user1Email, String user2Email); // Modificado
    List<ChatMessage> getMessagesForRecipient(String recipient); // Puedes mantenerlo si es necesario
    List<RecentConversationOutputDto> getRecentConversationsForUser(String userEmail); // Nuevo método
    void markMessagesAsRead(String recipientEmail, String senderEmail);
}
