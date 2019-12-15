package com.ddd.airplane.chat;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.room.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/room/{roomId}/chat")
    public void chat(
            @DestinationVariable Long roomId,
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        Room room = (Room) sessionAttributes.get("room");
        if (room == null || !roomId.equals(room.getRoomId())) {
            throw new RoomInvalidException(roomId);
        }

        Account account = (Account) sessionAttributes.get("account");

        messagingTemplate.convertAndSend(
                MessageFormat.format("/topic/room/{0}", roomId),
                new ChatMessage(
                        ChatMessageType.CHAT,
                        roomId,
                        account.getEmail(),
                        account.getNickname(),
                        chatMessage.getContent())
        );
    }
}
