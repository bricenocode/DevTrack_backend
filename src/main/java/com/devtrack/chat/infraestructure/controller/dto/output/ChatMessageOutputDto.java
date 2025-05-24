package com.devtrack.chat.infraestructure.controller.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageOutputDto {
    private String id;
    private String sender;
    private String recipient;
    private String content;
    private LocalDateTime timestamp;
    private boolean isOwnMessage;
}
