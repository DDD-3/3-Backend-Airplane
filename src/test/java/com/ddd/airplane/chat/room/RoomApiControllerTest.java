package com.ddd.airplane.chat.room;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.AccountDto;
import com.ddd.airplane.account.AccountService;
import com.ddd.airplane.chat.message.Message;
import com.ddd.airplane.chat.message.MessageService;
import com.ddd.airplane.common.AppProperties;
import com.ddd.airplane.common.BaseControllerTest;
import com.ddd.airplane.subject.SubjectService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomApiControllerTest extends BaseControllerTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private RoomService roomService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private MessageService messageService;

    private Account account;
    private String bearerToken;

    @Before
    public void setUp() throws Exception {
        accountService.deleteAll();
        bearerToken = getBearerToken();
    }

    @Test
    public void getRoom() throws Exception {
        // Given
        Room given = roomService.createRoom("주제", "설명");
        subjectService.subscribe(given.getSubject().getSubjectId(), account);
        LocalDateTime now = LocalDateTime.now();
        subjectService.addSchedule(given.getSubject().getSubjectId(), now.minusHours(2), now.plusHours(2));
        subjectService.addSchedule(given.getSubject().getSubjectId(), now.plusDays(1), now.plusDays(2));

        // When & Then
        mockMvc.perform(
                get("/api/v1/rooms/{roomId}", given.getRoomId())
                    .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("roomId").value(given.getRoomId()))
                .andExpect(jsonPath("subject.subjectId").value(given.getSubject().getSubjectId()))
                .andExpect(jsonPath("subject.name").value(given.getSubject().getName()))
                .andExpect(jsonPath("subject.description").value(given.getSubject().getDescription()))
                .andExpect(jsonPath("subject.scheduleList", hasSize(2)))
                .andExpect(jsonPath("subject.subscribeCount").value(1))
                .andExpect(jsonPath("userCount").exists());
    }

    @Test
    public void getSubscribedRooms() throws Exception {
        // Given
        Room given = generateRoom(1);
        Room given2 = generateRoom(2);
        Room given3 = generateRoom(3);
        subjectService.subscribe(given.getSubject().getSubjectId(), account);
        subjectService.subscribe(given2.getSubject().getSubjectId(), account);
        subjectService.subscribe(given3.getSubject().getSubjectId(), account);
        LocalDateTime now = LocalDateTime.now();
        subjectService.addSchedule(given.getSubject().getSubjectId(), now.minusHours(2), now.plusHours(2));
        subjectService.addSchedule(given.getSubject().getSubjectId(), now.plusDays(1), now.plusDays(2));

        // When & Then
        mockMvc.perform(
                get("/api//v1/roomsOfSubscribedSubjects")
                        .param("pageNum", String.valueOf(1))
                        .param("pageSize", String.valueOf(2))
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").value(hasSize(2)))
                .andExpect(jsonPath("$.items[0].roomId").exists())
                .andExpect(jsonPath("$.items[0].subject.subjectId").exists())
                .andExpect(jsonPath("$.items[0].subject.name").exists())
                .andExpect(jsonPath("$.items[0].subject.description").exists())
                .andExpect(jsonPath("$.items[0].subject.scheduleList").exists())
                .andExpect(jsonPath("$.items[0].subject.subscribeCount").exists())
                .andExpect(jsonPath("$.items[0].userCount").exists())
                .andExpect(jsonPath("$.pageInfo.pageNum").value(1))
                .andExpect(jsonPath("$.pageInfo.pageSize").value(2));
    }


    @Test
    public void getRecentMessagedRooms() throws Exception {
        // Given
        Room given = generateRoom(4);
        Room given2 = generateRoom(5);
        Room given3 = generateRoom(6);

        subjectService.subscribe(given.getSubject().getSubjectId(), account);
        subjectService.subscribe(given2.getSubject().getSubjectId(), account);
        subjectService.subscribe(given3.getSubject().getSubjectId(), account);

        LocalDateTime now = LocalDateTime.now();
        subjectService.addSchedule(given.getSubject().getSubjectId(), now.minusHours(2), now.plusHours(2));
        subjectService.addSchedule(given.getSubject().getSubjectId(), now.plusDays(1), now.plusDays(2));

        messageService.createMessage(Message.builder().roomId(given.getRoomId()).senderId(account.getEmail()).content("1-1").build());
        messageService.createMessage(Message.builder().roomId(given.getRoomId()).senderId(account.getEmail()).content("1-2").build());
        messageService.createMessage(Message.builder().roomId(given.getRoomId()).senderId(account.getEmail()).content("1-3").build());
        messageService.createMessage(Message.builder().roomId(given3.getRoomId()).senderId(account.getEmail()).content("3-1").build());

        // When & Then
        mockMvc.perform(
                get("/api//v1/recentMessagedRooms")
                        .param("pageNum", String.valueOf(1))
                        .param("pageSize", String.valueOf(2))
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").value(hasSize(2)))
                .andExpect(jsonPath("$.items[0].roomId").exists())
                .andExpect(jsonPath("$.items[0].subject.subjectId").exists())
                .andExpect(jsonPath("$.items[0].subject.name").exists())
                .andExpect(jsonPath("$.items[0].subject.description").exists())
                .andExpect(jsonPath("$.items[0].subject.scheduleList").exists())
                .andExpect(jsonPath("$.items[0].subject.subscribeCount").exists())
                .andExpect(jsonPath("$.items[0].userCount").exists())
                .andExpect(jsonPath("$.pageInfo.pageNum").value(1))
                .andExpect(jsonPath("$.pageInfo.pageSize").value(2));
    }

    private String getBearerToken() throws Exception {
        return "Bearer " + getAccessToken();
    }

    private String getAccessToken() throws Exception {
        String email = "sample@gmail.com";
        String password = "password";
        String nickname = "sample";

        AccountDto accountDto = AccountDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        account = accountService.createAccount(accountDto);

        ResultActions perform = mockMvc.perform(
                post("/oauth/token")
                        .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                        .param("username", email)
                        .param("password", password)
                        .param("grant_type", "password")
        );

        String responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
    }

    private Room generateRoom(int index) {
        return roomService.createRoom("Room " + index, "description " + index);
    }
}