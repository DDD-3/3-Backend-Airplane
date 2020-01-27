package com.ddd.airplane.chat.room;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.CurrentAccount;
import com.ddd.airplane.chat.message.Message;
import com.ddd.airplane.chat.message.MessageDto;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RoomApiController {
    private final RoomService roomService;
    private final MessageService messageService;

    @GetMapping("/v1/rooms/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public RoomDetailDto getRoom(
            @PathVariable Long roomId,
            @CurrentAccount Account account
    ) {
        Room room = roomService.getRoom(roomId, account);
        return new RoomDetailDto(room);
    }

    @GetMapping("/v1/roomsOfSubscribedSubjects")
    @ResponseStatus(HttpStatus.OK)
    public PageContent<RoomSubscribeDto> getSubscribedRooms(
            @CurrentAccount Account account,
            PageInfo pageInfo
    ) {
        List<Room> rooms = roomService.getSubscribedRooms(account, pageInfo);
        List<RoomSubscribeDto> roomDtoList = rooms.stream()
                .map(RoomSubscribeDto::new)
                .collect(Collectors.toList());
        return new PageContent<>(roomDtoList, pageInfo);
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
        List<Message> messages = messageService.getMessagesInRoom(criteria);
        List<MessageDto> messageDtoList = messages.stream()
                .map(MessageDto::new)
                .collect(Collectors.toList());

        return Map.of("messages", messageDtoList);
    }
}
