package com.ddd.airplane.subject;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.AccountCreateRequest;
import com.ddd.airplane.account.AccountService;
import com.ddd.airplane.chat.room.Room;
import com.ddd.airplane.chat.room.RoomService;
import com.ddd.airplane.common.BaseServiceTest;
import com.ddd.airplane.subject.schedule.SubjectSchedule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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
                AccountCreateRequest.builder()
                        .email("sample@gmail.com")
                        .password("password")
                        .build());

        account2 = accountService.createAccount(
                AccountCreateRequest.builder()
                        .email("sample2@gmail.com")
                        .password("password")
                        .build());
    }

    @Test
    public void subscribed() {
        subjectService.subscribe(room.getSubject().getSubjectId(), account1);
        subjectService.unsubscribe(room.getSubject().getSubjectId(), account2);

        Assert.assertTrue(subjectService.subscribed(room.getSubject().getSubjectId(), account1));
        Assert.assertFalse(subjectService.subscribed(room.getSubject().getSubjectId(), account2));
    }

    @Test
    public void getSchedules() {
        LocalDateTime now = LocalDateTime.now();
        subjectService.addSchedule(room.getSubject().getSubjectId(), now.minusHours(2), now.plusHours(2));
        subjectService.addSchedule(room.getSubject().getSubjectId(), now.plusDays(1), now.plusDays(2));

        List<SubjectSchedule> schedules = subjectService.getSchedules(room.getSubject().getSubjectId());
        schedules.forEach(schedule -> log.info(schedule.toString()));
    }
}