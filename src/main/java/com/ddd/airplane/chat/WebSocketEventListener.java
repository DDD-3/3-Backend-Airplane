package com.ddd.airplane.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleConnectedListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleSubscribeListener(SessionSubscribeEvent event) {
        //
    }

    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = (String) headerAccessor.getSessionAttributes().get("email");
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");

        if (email != null) {
            log.info("User Disconnected : " + email);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessageType.LEAVE);
            chatMessage.setSenderId(email);

            messagingTemplate.convertAndSend("/topic/room/" + roomId, chatMessage);
        }
    }
}
