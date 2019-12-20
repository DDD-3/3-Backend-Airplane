package com.ddd.airplane.account;

import com.ddd.airplane.chat.room.Room;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountApiController {
    private final AccountService accountService;

    @PostMapping("/v1/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody @Valid AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @GetMapping("/v1/accounts/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(
            @PathVariable String email,
            @CurrentAccount Account account
    ) {
        return accountService.getAccount(email);
    }

    // TODO : 참여한 채팅방 목록
    @GetMapping("/v1/accounts/{email}/joinedRooms")
    @ResponseStatus(HttpStatus.OK)
    public List<Room> getJoinedRooms(
            @PathVariable String email,
            @CurrentAccount Account account,
            Pageable pageable
    ) {
        return List.of();
    }
}
