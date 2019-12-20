package com.ddd.airplane.common;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.AccountDto;
import com.ddd.airplane.account.AccountService;
import com.ddd.airplane.chat.message.Message;
import com.ddd.airplane.chat.message.MessageService;
import com.ddd.airplane.chat.room.Room;
import com.ddd.airplane.chat.room.RoomService;
import com.ddd.airplane.subject.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
@Profile("dev")
@Component
public class DataInitAppRunner implements ApplicationRunner {
    private final AccountService accountService;
    private final RoomService roomService;
    private final MessageService messageService;
    private final SubjectService subjectService;

    @Override
    public void run(ApplicationArguments args) {
        // account
        Account sample = accountService.createAccount(
                AccountDto.builder()
                        .email("sample@gmail.com")
                        .password("password")
                        .nickname("nickname")
                        .build()
        );

        Account foo = accountService.createAccount(
                AccountDto.builder()
                        .email("foo@gmail.com")
                        .password("password")
                        .nickname("foo")
                        .build()
        );

        Account bar = accountService.createAccount(
                AccountDto.builder()
                        .email("bar@gmail.com")
                        .password("password")
                        .nickname("bar")
                        .build()
        );

        // room 1
        Room r1 = roomService.createRoom("호텔 델루나", "호텔 숙박 이야기");
        messageService.createMessage(
                Message.builder()
                        .roomId(r1.getRoomId())
                        .senderId(sample.getEmail())
                        .content("Hello, Room 1")
                        .build()
        );
        messageService.createMessage(
                Message.builder()
                        .roomId(r1.getRoomId())
                        .senderId(foo.getEmail())
                        .content("Hi, Room 1")
                        .build()
        );
        subjectService.subscribe(r1.getSubject().getSubjectId(), sample);
        subjectService.subscribe(r1.getSubject().getSubjectId(), foo);
        subjectService.unsubscribe(r1.getSubject().getSubjectId(), bar);

        LocalDateTime now = LocalDateTime.now();
        subjectService.addSchedule(r1.getSubject().getSubjectId(), now.minusHours(2), now.plusHours(1));
        subjectService.addSchedule(r1.getSubject().getSubjectId(), now.plusDays(1), now.plusDays(2));

        // room 2
        Room r2 = roomService.createRoom("효리네 민박", "효리가 밥 만드는 이야기");
        messageService.createMessage(
                Message.builder()
                        .roomId(r2.getRoomId())
                        .senderId(sample.getEmail())
                        .content("Hello, Room 2")
                        .build()
        );
        messageService.createMessage(
                Message.builder()
                        .roomId(r2.getRoomId())
                        .senderId(foo.getEmail())
                        .content("Hi, Room 2")
                        .build()
        );

    }
}
