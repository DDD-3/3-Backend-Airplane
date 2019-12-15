package com.ddd.airplane.chat;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.message.Message;
import com.ddd.airplane.message.MessageService;
import com.ddd.airplane.room.Room;
import com.ddd.airplane.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
    private final MessageService messageService;
    private final RedisPublisher redisPublisher;
    private final RoomService roomService;

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

        Message message = messageService.createMessage(
                Message.builder()
                        .roomId(room.getRoomId())
                        .senderId(account.getEmail())
                        .content(chatMessage.getContent())
                        .build());

        redisPublisher.publish(
                roomService.getTopic(room.getRoomId()),
                ChatMessage.builder()
                        .type(ChatMessageType.CHAT)
                        .messageId(message.getMessageId())
                        .roomId(room.getRoomId())
                        .senderId(account.getEmail())
                        .senderNickName(account.getNickname())
                        .content(message.getContent())
                        .build());
    }
}
