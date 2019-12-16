package com.ddd.airplane.chat.room;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.chat.message.MessagePayload;
import com.ddd.airplane.chat.message.MessagePayloadType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic roomTopic;

    public Room createRoom(String name) {
        return roomRepository.save(name);
    }

    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId);
    }

    public void joinRoom(Room room, Account account) {
        Long userCount = roomRepository.incrementUserCount(room.getRoomId());

        redisTemplate.convertAndSend(
                roomTopic.getTopic(),
                MessagePayload.builder()
                        .type(MessagePayloadType.JOIN)
                        .roomId(room.getRoomId())
                        .senderId(account.getEmail())
                        .senderNickName(account.getNickname())
                        .userCount(userCount)
                        .build());
    }

    public void leaveRoom(Room room, Account account) {
        Long userCount = roomRepository.decrementUserCount(room.getRoomId());

        redisTemplate.convertAndSend(
                roomTopic.getTopic(),
                MessagePayload.builder()
                        .type(MessagePayloadType.LEAVE)
                        .roomId(room.getRoomId())
                        .senderId(account.getEmail())
                        .senderNickName(account.getNickname())
                        .userCount(userCount)
                        .build());
    }
}
