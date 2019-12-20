package com.ddd.airplane.chat.room;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.CurrentAccount;
import com.ddd.airplane.chat.message.Message;
import com.ddd.airplane.common.PageContent;
import com.ddd.airplane.common.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RoomApiController {
    private final RoomService roomService;

    @GetMapping("/v1/rooms/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public Room getRoom(
            @PathVariable Long roomId,
            @CurrentAccount Account account
    ) {
        return roomService.getRoom(roomId);
    }

    @GetMapping("/v1/roomsOfSubscribedSubjects")
    @ResponseStatus(HttpStatus.OK)
    public PageContent<Room> getSubscribedRooms(
            @CurrentAccount Account account,
            PageInfo pageInfo
    ) {
        List<Room> subscribedRooms = roomService.getSubscribedRooms(account, pageInfo);
        return new PageContent<>(subscribedRooms, pageInfo);
    }

    // TODO : 많이 참여한 채팅방
    @GetMapping("/v1/wellJoinedRooms")
    @ResponseStatus(HttpStatus.OK)
    public List<Room> getWellJoinedRooms(
            @CurrentAccount Account account
    ) {
        return List.of();
    }

    // TODO : 방송사별 채팅방 일정
    // TODO : 카테고리별 채팅방 일정
    @GetMapping("/v1/rooms")
    @ResponseStatus(HttpStatus.OK)
    public List<Room> getRooms(
            Pageable pageable,
            @CurrentAccount Account account
    ) {
        return List.of();
    }

    // TODO : 특정 채팅방 내 메세지 조회
    @GetMapping("/v1/rooms/{roomId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getMessages(
            @PathVariable String roomId,
            Pageable pageable,
            @CurrentAccount Account account
    ) {
        return List.of();
    }
}
