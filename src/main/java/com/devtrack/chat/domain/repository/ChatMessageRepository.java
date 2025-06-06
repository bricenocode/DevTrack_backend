package com.devtrack.chat.domain.repository;

import com.devtrack.chat.domain.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findBySenderAndRecipientOrderByTimestampAsc(String sender, String recipient);
    List<ChatMessage> findByRecipientOrderByTimestampAsc(String recipient);
    List<ChatMessage> findBySenderOrRecipientOrderByTimestampDesc(String sender, String recipient);
    List<ChatMessage> findByRecipientAndSenderAndIsReadFalse(String recipient, String sender);

}