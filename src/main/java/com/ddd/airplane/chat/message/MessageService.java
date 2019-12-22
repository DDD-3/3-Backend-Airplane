package com.ddd.airplane.chat.message;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.chat.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic roomTopic;

    public List<Message> getMessagesInRoom(MessageGetCriteria criteria) {
        return messageRepository.selectMessagesInRoom(criteria);
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public void sendMessage(MessagePayload messagePayload, Room room, Account account) {
        Message message = createMessage(
                Message.builder()
                        .roomId(room.getRoomId())
                        .senderId(account.getEmail())
                        .content(messagePayload.getContent())
                        .build());

        redisTemplate.convertAndSend(
                roomTopic.getTopic(),
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
