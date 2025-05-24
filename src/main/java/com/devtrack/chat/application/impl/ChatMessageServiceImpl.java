package com.devtrack.chat.application.impl;

import com.devtrack.chat.application.ChatMessageService;
import com.devtrack.chat.domain.entity.ChatMessage;
import com.devtrack.chat.domain.repository.ChatMessageRepository;
import com.devtrack.chat.infraestructure.controller.dto.output.RecentConversationOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar para transacciones

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setRead(false); // Mensajes nuevos no están leídos por el destinatario
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> getChatHistory(String user1Email, String user2Email) {
        List<ChatMessage> messages1 = chatMessageRepository.findBySenderAndRecipientOrderByTimestampAsc(user1Email, user2Email);
        List<ChatMessage> messages2 = chatMessageRepository.findBySenderAndRecipientOrderByTimestampAsc(user2Email, user1Email);

        List<ChatMessage> combinedMessages = new ArrayList<>();
        combinedMessages.addAll(messages1);
        combinedMessages.addAll(messages2);

        combinedMessages.sort(Comparator.comparing(ChatMessage::getTimestamp));

        return combinedMessages;
    }

    @Override
    public List<ChatMessage> getMessagesForRecipient(String recipient) {
        return chatMessageRepository.findByRecipientOrderByTimestampAsc(recipient);
    }

    @Override
    public List<RecentConversationOutputDto> getRecentConversationsForUser(String userEmail) {
        List<ChatMessage> allRelevantMessages = chatMessageRepository.findBySenderOrRecipientOrderByTimestampDesc(userEmail, userEmail);

        Map<String, RecentConversationOutputDto> recentConversationsMap = new HashMap<>();

        for (ChatMessage msg : allRelevantMessages) {
            String participantEmail = msg.getSender().equals(userEmail) ? msg.getRecipient() : msg.getSender();

            if (!recentConversationsMap.containsKey(participantEmail)) {
                // Para la primera vez que encontramos un mensaje para esta conversación,
                // o si el mensaje actual es más reciente que el ya registrado para este participante.
                // Aquí, el `hasNewMessages` se calculará basándose en el conteo de no leídos.

                // Contar mensajes no leídos donde el usuario actual es el destinatario y el otro participante es el remitente
                long unreadCountForParticipant = chatMessageRepository.findByRecipientAndSenderAndIsReadFalse(userEmail, participantEmail).size();

                recentConversationsMap.put(participantEmail, new RecentConversationOutputDto(
                        participantEmail,
                        msg.getContent(),
                        msg.getTimestamp().toString(),
                        unreadCountForParticipant > 0 // True si hay 1 o más mensajes no leídos
                ));
            } else {
                // Si ya existe una entrada para este participante, solo actualizamos si el mensaje es más reciente
                // y mantenemos el `hasNewMessages` del mensaje más reciente, o recalculamos si la entrada existía
                // y el mensaje actual es el más reciente.
                RecentConversationOutputDto existingEntry = recentConversationsMap.get(participantEmail);
                if (msg.getTimestamp().isAfter(LocalDateTime.parse(existingEntry.getLastMessageTimestamp()))) {
                    // Si este mensaje es más reciente, actualizamos el contenido y timestamp
                    // y recalculamos la bandera de no leídos.
                    long unreadCountForParticipant = chatMessageRepository.findByRecipientAndSenderAndIsReadFalse(userEmail, participantEmail).size();
                    recentConversationsMap.put(participantEmail, new RecentConversationOutputDto(
                            participantEmail,
                            msg.getContent(),
                            msg.getTimestamp().toString(),
                            unreadCountForParticipant > 0
                    ));
                } else if (existingEntry.isHasNewMessages() == false && msg.getRecipient().equals(userEmail) && !msg.isRead()) {
                    // Si la entrada existente NO tenía mensajes nuevos, pero este mensaje (que es más antiguo) sí lo tiene y es para el usuario actual,
                    // significa que la conversación YA tiene mensajes no leídos. Actualizamos la bandera sin cambiar el último mensaje.
                    // Esto evita que un mensaje antiguo no leído no se refleje en la lista de recientes.
                    recentConversationsMap.put(participantEmail, new RecentConversationOutputDto(
                            existingEntry.getEmail(),
                            existingEntry.getLastMessageContent(),
                            existingEntry.getLastMessageTimestamp(),
                            true // Hay al menos un mensaje no leído en esta conversación
                    ));
                }
            }
        }

        List<RecentConversationOutputDto> sortedConversations = new ArrayList<>(recentConversationsMap.values());
        sortedConversations.sort(Comparator.comparing(RecentConversationOutputDto::getLastMessageTimestamp, Comparator.reverseOrder()));

        return sortedConversations;
    }

    @Override
    @Transactional // Para asegurar que todas las actualizaciones se realicen como una unidad.
    public void markMessagesAsRead(String recipientEmail, String senderEmail) {
        List<ChatMessage> unreadMessages = chatMessageRepository.findByRecipientAndSenderAndIsReadFalse(recipientEmail, senderEmail);
        for (ChatMessage msg : unreadMessages) {
            msg.setRead(true);
            chatMessageRepository.save(msg);
        }
    }
}