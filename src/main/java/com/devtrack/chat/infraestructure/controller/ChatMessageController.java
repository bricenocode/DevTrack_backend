package com.devtrack.chat.infraestructure.controller;

import com.devtrack.chat.application.ChatMessageService;
import com.devtrack.chat.domain.entity.ChatMessage;
import com.devtrack.chat.infraestructure.controller.dto.output.RecentConversationOutputDto;
import com.devtrack.configuration.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final ChatHandler chatHandler;
    
    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage chatMessage) {
        ChatMessage savedMessage = chatMessageService.sendMessage(chatMessage);
        chatHandler.sendMessageToUser(savedMessage.getRecipient(), savedMessage);
        chatHandler.sendMessageToUser(savedMessage.getSender(), savedMessage);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }
    
    @GetMapping("/history/{otherUserEmail}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String otherUserEmail) {
        String currentUserEmail = getCurrentUserEmail();
        if (currentUserEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<ChatMessage> history = chatMessageService.getChatHistory(currentUserEmail, otherUserEmail);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @GetMapping("/received/{recipient}")
    public ResponseEntity<List<ChatMessage>> getReceivedMessages(@PathVariable String recipient) {
        List<ChatMessage> receivedMessages = chatMessageService.getMessagesForRecipient(recipient);
        return new ResponseEntity<>(receivedMessages, HttpStatus.OK);
    }
    
    @GetMapping("/recent-conversations")
    public ResponseEntity<List<RecentConversationOutputDto>> getRecentConversations() {
        String currentUserEmail = getCurrentUserEmail();
        if (currentUserEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<RecentConversationOutputDto> recentConversations = chatMessageService.getRecentConversationsForUser(currentUserEmail);
        return new ResponseEntity<>(recentConversations, HttpStatus.OK);
    }
    
    @PostMapping("/mark-as-read")
    public ResponseEntity<Void> markMessagesAsRead(@RequestBody Map<String, String> requestBody) {
        String recipientEmail = requestBody.get("recipientEmail"); 
        String senderEmail = requestBody.get("senderEmail");

        String currentUserEmail = getCurrentUserEmail();
        if (currentUserEmail == null || !currentUserEmail.equals(recipientEmail)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (senderEmail == null || senderEmail.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        chatMessageService.markMessagesAsRead(recipientEmail, senderEmail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }
}