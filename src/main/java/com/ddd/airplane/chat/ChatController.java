package com.ddd.airplane.chat;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.chat.message.MessagePayload;
import com.ddd.airplane.chat.message.MessagePayloadType;
import com.ddd.airplane.chat.message.Message;
import com.ddd.airplane.chat.message.MessageService;
import com.ddd.airplane.chat.room.Room;
import com.ddd.airplane.chat.room.RoomInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
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
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;

    @MessageMapping("/room/{roomId}/chat")
    public void chat(
            @DestinationVariable Long roomId,
            @Payload MessagePayload messagePayload,
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
                        .content(messagePayload.getContent())
                        .build());

        redisTemplate.convertAndSend(
                channelTopic.getTopic(),
                MessagePayload.builder()
                        .type(MessagePayloadType.CHAT)
                        .messageId(message.getMessageId())
                        .roomId(room.getRoomId())
                        .senderId(account.getEmail())
                        .senderNickName(account.getNickname())
                        .content(message.getContent())
                        .build());
    }
}
