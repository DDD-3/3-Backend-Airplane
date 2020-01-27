package com.ddd.airplane.chat.room;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.chat.message.MessageService;
import com.ddd.airplane.chat.payload.ChatPayload;
import com.ddd.airplane.chat.payload.ChatPayloadType;
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
    private final MessageService messageService;
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

    public Room getRoom(Long roomId, Account account) {
        Room room = roomRepository.findById(roomId);
        if (room == null) {
            throw new RoomNotFoundException(roomId);
        }

        room.setSubject(subjectService.getSubject(room.getSubject().getSubjectId(), account));
        room.setMessages(messageService.getRecentMessagesInRoom(roomId));
        room.setLiked(subjectService.liked(room.getSubject().getSubjectId(), account));

        return room;
    }

    List<Room> getSubscribedRooms(Account account, PageInfo pageInfo) {
        List<Room> rooms = roomRepository.selectSubscribedRooms(account.getEmail(), pageInfo);
        rooms.forEach(r -> r.setSubject(subjectService.getSubject(r.getSubject().getSubjectId(), account)));

        return rooms;
    }

    public List<Room> getRecentMessagedRooms(Account account, PageInfo pageInfo) {
        List<Room> rooms = roomRepository.selectRecentMessagedRooms(account.getEmail(), pageInfo);
        rooms.forEach(r -> r.setSubject(subjectService.getSubject(r.getSubject().getSubjectId(), account)));

        return rooms;
    }

    public void joinRoom(Room room, Account account) {
        Long userCount = roomRepository.incrementUserCount(room.getRoomId());

        redisTemplate.convertAndSend(
                roomTopic.getTopic(),
                ChatPayload.builder()
                        .type(ChatPayloadType.JOIN)
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
                ChatPayload.builder()
                        .type(ChatPayloadType.LEAVE)
                        .roomId(room.getRoomId())
                        .senderId(account.getEmail())
                        .senderNickName(account.getNickname())
                        .userCount(userCount)
                        .build());
    }
}
