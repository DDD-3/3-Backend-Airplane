package com.ddd.airplane.chat.room;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.chat.message.MessagePayload;
import com.ddd.airplane.chat.message.MessagePayloadType;
import com.ddd.airplane.common.PageInfo;
import com.ddd.airplane.subject.Subject;
import com.ddd.airplane.subject.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final SubjectService subjectService;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic roomTopic;

    public Room createRoom(String name, String description) {
        Subject subject = subjectService.createSubject(
                Subject.builder()
                        .name(name)
                        .description(description)
                        .build());

        Room room = roomRepository.save(subject);
        room.setSubject(subject);

        return room;
    }

    public Room getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId);
        Subject subject = subjectService.getSubject(room.getSubject().getSubjectId());
        room.setSubject(subject);

        return room;
    }

    List<Room> getSubscribedRooms(Account account, PageInfo pageInfo) {
        List<Room> rooms = roomRepository.selectSubscribedRooms(account.getEmail(), pageInfo);
        rooms.forEach(r -> r.setSubject(subjectService.getSubject(r.getSubject().getSubjectId())));

        return rooms;
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
