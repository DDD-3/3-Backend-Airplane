package com.ddd.airplane.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public void handleMessage(String message) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
            String destination = MessageFormat.format("/topic/room/{0}", chatMessage.getRoomId());
            simpMessageSendingOperations.convertAndSend(destination, chatMessage);
            log.info("ChatMessage : {}", chatMessage.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
