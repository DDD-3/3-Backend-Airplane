package com.ddd.airplane.chat;

import com.ddd.airplane.chat.payload.ChatPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatSubscriber {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public void handleMessage(String message) {
        try {
            ChatPayload chatPayload = objectMapper.readValue(message, ChatPayload.class);
            String destination = MessageFormat.format("/topic/room/{0}", chatPayload.getRoomId());
            simpMessageSendingOperations.convertAndSend(destination, chatPayload);
            log.info("ChatPayload : {}", chatPayload.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
