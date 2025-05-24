package com.devtrack.configuration;

import com.devtrack.chat.application.ChatMessageService;
import com.devtrack.chat.domain.entity.ChatMessage;
import com.devtrack.users.application.UserService;
import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = Logger.getLogger(ChatHandler.class.getName());
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private final UserService userService;
    private final ChatMessageService chatMessageService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = extractToken(session.getUri().getQuery());
        if (token != null && jwtUtil.validateJwtToken(token)) {
            String userEmail = jwtUtil.getEmailFromToken(token);
            sessions.put(userEmail, session);
            LOGGER.info("Usuario conectado: " + userEmail + " - Session ID: " + session.getId());
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    Map.of("type", "info", "message", "Conectado al servicio de chat. Su email: " + userEmail)
            )));
        } else {
            LOGGER.warning("Conexión rechazada: token inválido o ausente para sesión " + session.getId());
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    private String extractToken(String query) {
        if (query != null && query.contains("token=")) {
            int tokenStartIndex = query.indexOf("token=") + 6;
            int tokenEndIndex = query.indexOf("&", tokenStartIndex);
            if (tokenEndIndex == -1) {
                return query.substring(tokenStartIndex);
            } else {
                return query.substring(tokenStartIndex, tokenEndIndex);
            }
        }
        return null;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.entrySet().stream()
                .filter(entry -> entry.getValue().equals(session))
                .findFirst()
                .ifPresent(entry -> {
                    sessions.remove(entry.getKey());
                    LOGGER.info("Usuario desconectado: " + entry.getKey() + " - Session ID: " + session.getId());
                });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        try {
            Map<String, String> messageData = objectMapper.readValue(payload, new com.fasterxml.jackson.core.type.TypeReference<Map<String, String>>() {});
            String type = messageData.get("type");

            if (type == null) {
                LOGGER.warning("Mensaje recibido sin tipo definido: " + payload);
                return;
            }

            switch (type) {
                case "private":
                    handlePrivateMessage(session, messageData);
                    break;
                case "searchUser":
                    String query = messageData.get("query");
                    if (query != null) {
                        handleSearchUser(session, query);
                    } else {
                        LOGGER.warning("Mensaje searchUser sin 'query'.");
                    }
                    break;
                default:
                    LOGGER.warning("Tipo de mensaje desconocido: " + type);
                    break;
            }
        } catch (IOException e) {
            LOGGER.throwing("ChatHandler", "handleTextMessage - Error al procesar el mensaje: " + payload, e);
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    Map.of("type", "error", "message", "Formato de mensaje inválido.")
            )));
        }
    }

    private void handlePrivateMessage(WebSocketSession session, Map<String, String> messageData) throws IOException {
        String recipientEmail = messageData.get("recipient");
        String content = messageData.get("content");
        String senderEmail = getEmailFromSession(session); // This is the person sending the message

        if (senderEmail == null) {
            LOGGER.warning("No se pudo obtener el email del remitente de la sesión.");
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    Map.of("type", "error", "message", "Remitente no autenticado.")
            )));
            return;
        }

        if (recipientEmail == null || content == null) {
            LOGGER.warning("Mensaje privado incompleto (recipient o content es nulo).");
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    Map.of("type", "error", "message", "Mensaje privado incompleto (requiere 'recipient' y 'content').")
            )));
            return;
        }

        ChatMessage newMessage = ChatMessage.builder()
                .sender(senderEmail)
                .recipient(recipientEmail)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();

        ChatMessage savedMessage = chatMessageService.sendMessage(newMessage);
        LOGGER.info("Mensaje guardado en BD: " + savedMessage);
        sendChatMessageToSession(session, savedMessage, true); 
        WebSocketSession recipientSession = sessions.get(recipientEmail);
        if (recipientSession != null && recipientSession.isOpen()) {
            sendChatMessageToSession(recipientSession, savedMessage, false);
            LOGGER.info("Mensaje en tiempo real enviado a: " + recipientEmail);
        } else {
            LOGGER.info("Destinatario " + recipientEmail + " no está conectado. Mensaje guardado, no enviado en tiempo real.");
            // Optionally, inform the sender that the recipient is offline
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    Map.of("type", "info", "message", "El destinatario no está en línea, el mensaje será entregado cuando se conecte.")
            )));
        }
    }

    private void sendChatMessageToSession(WebSocketSession session, ChatMessage message, boolean isOwnMessage) throws IOException {
        Map<String, Object> messageMap = Map.of(
                "type", "private",
                "sender", message.getSender(),
                "recipient", message.getRecipient(),
                "content", message.getContent(),
                "timestamp", message.getTimestamp().toString(),
                "isOwnMessage", isOwnMessage
        );
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageMap)));
    }


    private void handleSearchUser(WebSocketSession session, String query) throws IOException {
        LOGGER.info("Búsqueda solicitada con query: " + query);
        String currentUserEmail = getEmailFromSession(session);
        LOGGER.info("currentUserEmail: " + currentUserEmail);
        if (currentUserEmail == null) {
            LOGGER.warning("No se pudo obtener el email del usuario para la búsqueda.");
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    Map.of("type", "error", "message", "Autenticación requerida para la búsqueda de usuarios.")
            )));
            return;
        }

        List<UserEntity> users = userService.findUsersByEmail(query);
        LOGGER.info("Resultados de la búsqueda desde UserUseCase: " + users);

        List<String> searchResults = users.stream()
                .filter(user -> !user.getEmail().equals(currentUserEmail))
                .map(UserEntity::getEmail)
                .toList();
        LOGGER.info("searchResults antes de enviar: " + searchResults);

        Map<String, Object> response = Map.of("type", "userSearchResults", "results", searchResults);
        String jsonResponse = objectMapper.writeValueAsString(response);
        LOGGER.info("Mensaje WebSocket enviado: " + jsonResponse);
        session.sendMessage(new TextMessage(jsonResponse));
    }

    private String getEmailFromSession(WebSocketSession session) {
        return sessions.entrySet().stream()
                .filter(entry -> entry.getValue().equals(session))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

 
    public void sendMessageToUser(String recipientEmail, ChatMessage message) {
        WebSocketSession recipientSession = sessions.get(recipientEmail);
        if (recipientSession != null && recipientSession.isOpen()) {
            try {
                sendChatMessageToSession(recipientSession, message, false);
                LOGGER.info("Mensaje programático enviado a: " + recipientEmail);
            } catch (IOException e) {
                LOGGER.severe("Error al enviar mensaje programático a " + recipientEmail + ": " + e.getMessage());
            }
        } else {
            LOGGER.info("Destinatario " + recipientEmail + " no está conectado. Mensaje no enviado en tiempo real.");
        }
    }
}