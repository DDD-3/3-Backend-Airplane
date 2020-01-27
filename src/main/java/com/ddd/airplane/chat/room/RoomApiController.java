package com.ddd.airplane.chat.room;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.CurrentAccount;
import com.ddd.airplane.chat.message.Message;
import com.ddd.airplane.chat.message.MessageGetCriteria;
import com.ddd.airplane.chat.message.MessageService;
import com.ddd.airplane.common.PageContent;
import com.ddd.airplane.common.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RoomApiController {
    private final RoomService roomService;
    private final MessageService messageService;

    @GetMapping("/v1/rooms/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public Room getRoom(
            @PathVariable Long roomId,
            @CurrentAccount Account account
    ) {
        return roomService.getRoom(roomId, account);
    }

    @GetMapping("/v1/roomsOfSubscribedSubjects")
    @ResponseStatus(HttpStatus.OK)
    public PageContent<Room> getSubscribedRooms(
            @CurrentAccount Account account,
            PageInfo pageInfo
    ) {
        List<Room> rooms = roomService.getSubscribedRooms(account, pageInfo);
        return new PageContent<>(rooms, pageInfo);
    }

    @GetMapping("/v1/recentMessagedRooms")
    @ResponseStatus(HttpStatus.OK)
    public PageContent<Room> getRecentMessagedRooms(
            @CurrentAccount Account account,
            PageInfo pageInfo
    ) {
        List<Room> rooms = roomService.getRecentMessagedRooms(account, pageInfo);
        return new PageContent<>(rooms, pageInfo);
    }

    // TODO : 1차 - 많이 참여한 채팅방
    @GetMapping("/v1/wellJoinedRooms")
    @ResponseStatus(HttpStatus.OK)
    public List<Room> getWellJoinedRooms(
            @CurrentAccount Account account
    ) {
        return List.of();
    }

    // TODO : 1차 - 방송사별 채팅방 일정
    // TODO : 1차 - 카테고리별 채팅방 일정
    @GetMapping("/v1/rooms")
    @ResponseStatus(HttpStatus.OK)
    public List<Room> getRooms(
            Pageable pageable,
            @CurrentAccount Account account
    ) {
        return List.of();
    }

    @GetMapping("/v1/rooms/{roomId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getMessages(
            @PathVariable Long roomId,
            MessageGetCriteria criteria,
            @CurrentAccount Account account
    ) {
        criteria.setRoomId(roomId);
        List<Message> messagesInRoom = messageService.getMessagesInRoom(criteria);
        return Map.of("messages", messagesInRoom);
    }
}
