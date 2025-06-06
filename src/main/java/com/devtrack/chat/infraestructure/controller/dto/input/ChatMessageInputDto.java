package com.devtrack.chat.infraestructure.controller.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageInputDto {
    @NotBlank(message = "El remitente no puede estar vacío")
    private String sender;
    @NotBlank(message = "El destinatario no puede estar vacío")
    private String recipient;
    @NotBlank(message = "El contenido del mensaje no puede estar vacío")
    private String content;
}
