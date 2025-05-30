package com.devtrack.chat.application;

import com.devtrack.chat.domain.entity.ChatMessage;
import com.devtrack.chat.infraestructure.controller.dto.output.RecentConversationOutputDto;

import java.util.List;

public interface ChatMessageService {
    ChatMessage sendMessage(ChatMessage chatMessage);
    List<ChatMessage> getChatHistory(String user1Email, String user2Email);
    List<ChatMessage> getMessagesForRecipient(String recipient);
    List<RecentConversationOutputDto> getRecentConversationsForUser(String userEmail);
    void markMessagesAsRead(String recipientEmail, String senderEmail);
}
