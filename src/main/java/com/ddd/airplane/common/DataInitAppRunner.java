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

import java.time.LocalDateTime;

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
        Account abc = accountService.createAccount(
                AccountDto.builder()
                        .email("abc@gmail.com")
                        .password("password")
                        .nickname("abc")
                        .build()
        );

        Account def = accountService.createAccount(
                AccountDto.builder()
                        .email("def@gmail.com")
                        .password("password")
                        .nickname("def")
                        .build()
        );

        Account ghi = accountService.createAccount(
                AccountDto.builder()
                        .email("ghi@gmail.com")
                        .password("password")
                        .nickname("ghi")
                        .build()
        );

        // room
        Room room1 = roomService.createRoom("사랑은 뷰티풀 인생은 원더풀", "뭔가 되기 위해 애썼으나 되지 못한 보통사람들의 인생재활극으로, 울퉁불퉁 보잘것없는 내 인생을 다시 사랑하고 소소하지만 확실한 행복을 찾아가는 '소확행' 드라마");
        Room room2 = roomService.createRoom("99억의 여자", "우연히 현찰 99억을 움켜쥔 여자가 세상과 맞서 싸우는 이야기");
        Room room3 = roomService.createRoom("VIP", "백화점 상위 1% VIP 고객을 관리하는 전담팀 사람들의 비밀스러운 프라이빗 오피스 멜로");

        // message
        messageService.createMessage(Message.builder().roomId(room1.getRoomId()).senderId(abc.getEmail()).content("abc, room 1, message 1").build());
        messageService.createMessage(Message.builder().roomId(room1.getRoomId()).senderId(def.getEmail()).content("def, Room 1, message 1").build());
        messageService.createMessage(Message.builder().roomId(room1.getRoomId()).senderId(abc.getEmail()).content("abc, room 1, message 2").build());

        messageService.createMessage(Message.builder().roomId(room2.getRoomId()).senderId(ghi.getEmail()).content("ghi, room 2, message 1").build());

        // subject subscribe
        subjectService.subscribe(room1.getSubject().getSubjectId(), abc);
        subjectService.subscribe(room2.getSubject().getSubjectId(), abc);

        subjectService.subscribe(room1.getSubject().getSubjectId(), def);

        subjectService.subscribe(room1.getSubject().getSubjectId(), ghi);
        subjectService.subscribe(room2.getSubject().getSubjectId(), ghi);
        subjectService.subscribe(room3.getSubject().getSubjectId(), ghi);

        // subject like
        subjectService.like(room1.getSubject().getSubjectId(), abc);

        // subject schedule
        subjectService.addSchedule(room1.getSubject().getSubjectId(), LocalDateTime.of(2019, 12, 10, 22, 0), LocalDateTime.of(2019, 12, 10, 23, 0));
        subjectService.addSchedule(room1.getSubject().getSubjectId(), LocalDateTime.of(2019, 12, 11, 22, 0), LocalDateTime.of(2019, 12, 11, 23, 0));
        subjectService.addSchedule(room1.getSubject().getSubjectId(), LocalDateTime.of(2019, 12, 12, 22, 0), LocalDateTime.of(2019, 12, 12, 23, 0));

        subjectService.addSchedule(room2.getSubject().getSubjectId(), LocalDateTime.of(2019, 12, 10, 12, 0), LocalDateTime.of(2019, 12, 10, 14, 30));
        subjectService.addSchedule(room2.getSubject().getSubjectId(), LocalDateTime.of(2019, 12, 11, 12, 0), LocalDateTime.of(2019, 12, 11, 14, 30));
        subjectService.addSchedule(room2.getSubject().getSubjectId(), LocalDateTime.of(2019, 12, 12, 12, 0), LocalDateTime.of(2019, 12, 12, 14, 30));
    }
}
