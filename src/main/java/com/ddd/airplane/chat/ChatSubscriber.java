package com.ddd.airplane.chat;

import com.ddd.airplane.chat.message.MessagePayload;
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
            MessagePayload messagePayload = objectMapper.readValue(message, MessagePayload.class);
            String destination = MessageFormat.format("/topic/room/{0}", messagePayload.getRoomId());
            simpMessageSendingOperations.convertAndSend(destination, messagePayload);
            log.info("MessagePayload : {}", messagePayload.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
