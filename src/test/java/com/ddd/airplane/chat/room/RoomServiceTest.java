package com.ddd.airplane.chat.room;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.AccountDto;
import com.ddd.airplane.account.AccountService;
import com.ddd.airplane.common.BaseServiceTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomServiceTest extends BaseServiceTest {
    @Autowired
    private RoomService roomService;
    @Autowired
    private AccountService accountService;

    @Before
    public void setUp() {
        accountService.deleteAll();
    }

    @Test
    public void getRoom() {
        // given
        Room given = roomService.createRoom("주제", "설명");
        Account account = createAccount();
        
        // when
        Room actual = roomService.getRoom(given.getRoomId(), account);

        // then
        Assert.assertEquals(given.getRoomId(), actual.getRoomId());
        Assert.assertEquals(given.getSubject().getName(), actual.getSubject().getName());
    }

    private Account createAccount() {
        String email = "sample@gmail.com";
        String password = "password";
        String nickname = "sample";

        AccountDto accountDto = AccountDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        return accountService.createAccount(accountDto);
    }
}