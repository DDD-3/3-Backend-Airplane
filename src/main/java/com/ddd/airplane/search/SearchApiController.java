package com.ddd.airplane.search;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.CurrentAccount;
import com.ddd.airplane.chat.room.Room;
import com.ddd.airplane.chat.room.RoomDto;
import com.ddd.airplane.chat.room.RoomService;
import com.ddd.airplane.common.PageContent;
import com.ddd.airplane.common.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SearchApiController {
    private final RoomService roomService;

    @GetMapping("/v1/search")
    @ResponseStatus(HttpStatus.OK)
    public PageContent<RoomDto> search(
            @RequestParam String query,
            PageInfo pageInfo,
            @CurrentAccount Account account
    ) {
        List<Room> rooms = roomService.getRoomsContainSubjectName(query, account, pageInfo);
        List<RoomDto> roomDtoList = rooms.stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
        return new PageContent<>(roomDtoList, pageInfo);
    }
}
