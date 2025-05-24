package com.devtrack.chat.infraestructure.controller.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentConversationOutputDto {
    private String email;
    private String lastMessageContent;
    private String lastMessageTimestamp;
    private boolean hasNewMessages;
}
