package com.ddd.airplane.subject;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.AccountDto;
import com.ddd.airplane.account.AccountService;
import com.ddd.airplane.chat.room.Room;
import com.ddd.airplane.chat.room.RoomService;
import com.ddd.airplane.common.BaseServiceTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SubjectServiceTest extends BaseServiceTest {
    @Autowired
    private RoomService roomService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private AccountService accountService;

    private Room room;
    private Account account1;
    private Account account2;

    @Before
    public void setup() {
        accountService.deleteAll();

        room = roomService.createRoom("주제", "설명");

        account1 = accountService.createAccount(
                AccountDto.builder()
                        .email("sample@gmail.com")
                        .password("password")
                        .nickname("sample")
                        .build());

        account2 = accountService.createAccount(
                AccountDto.builder()
                        .email("sample2@gmail.com")
                        .password("password")
                        .nickname("sample")
                        .build());
    }

    @Test
    public void subscribed() {
        subjectService.subscribe(room.getSubject().getSubjectId(), account1);
        subjectService.unsubscribe(room.getSubject().getSubjectId(), account2);

        Assert.assertTrue(subjectService.subscribed(room.getSubject().getSubjectId(), account1));
        Assert.assertFalse(subjectService.subscribed(room.getSubject().getSubjectId(), account2));
    }
}