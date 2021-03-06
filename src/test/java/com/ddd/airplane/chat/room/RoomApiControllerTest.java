package com.ddd.airplane.chat.room;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.AccountCreateRequest;
import com.ddd.airplane.account.AccountService;
import com.ddd.airplane.chat.message.Message;
import com.ddd.airplane.chat.message.MessageGetDirection;
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

import static org.hamcrest.Matchers.hasSize;
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
        subjectService.like(given.getSubject().getSubjectId(), account);
        messageService.createMessage(
                Message.builder()
                        .roomId(given.getRoomId())
                        .senderId(account.getEmail())
                        .content("1")
                        .build());

        // When & Then
        mockMvc.perform(
                get("/api/v1/rooms/{roomId}", given.getRoomId())
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("roomId").value(given.getRoomId()))
                .andExpect(jsonPath("subjectId").value(given.getSubject().getSubjectId()))
                .andExpect(jsonPath("subjectName").value(given.getSubject().getName()))
                .andExpect(jsonPath("subjectDescription").value(given.getSubject().getDescription()))
                .andExpect(jsonPath("upcomingSubjectSchedule").exists())
                .andExpect(jsonPath("subjectSubscribeCount").value(1))
                .andExpect(jsonPath("subjectSubscribed").value(true))
                .andExpect(jsonPath("recentMessages").exists())
                .andExpect(jsonPath("roomUserCount").exists())
                .andExpect(jsonPath("roomLiked").value(true));
    }

    @Test
    public void getMessages_backward() throws Exception {
        // Given
        Room room = generateRoom(1);
        Message message1 = messageService.createMessage(Message.builder().roomId(room.getRoomId()).senderId(account.getEmail()).content("1").build());
        Message message2 = messageService.createMessage(Message.builder().roomId(room.getRoomId()).senderId(account.getEmail()).content("2").build());
        Message message3 = messageService.createMessage(Message.builder().roomId(room.getRoomId()).senderId(account.getEmail()).content("3").build());
        Message message4 = messageService.createMessage(Message.builder().roomId(room.getRoomId()).senderId(account.getEmail()).content("4").build());
        Message message5 = messageService.createMessage(Message.builder().roomId(room.getRoomId()).senderId(account.getEmail()).content("5").build());

        // When & Then
        mockMvc.perform(
                get("/api/v1/rooms/{roomId}/messages", room.getRoomId())
                        .param("baseMessageId", String.valueOf(message4.getMessageId()))
                        .param("size", String.valueOf(2))
                        .param("direction", MessageGetDirection.BACKWARD.name())
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messages", hasSize(2)));
    }

    @Test
    public void getMessages_forward() throws Exception {
        // Given
        Room room = generateRoom(1);
        Message message1 = messageService.createMessage(Message.builder().roomId(room.getRoomId()).senderId(account.getEmail()).content("1").build());
        Message message2 = messageService.createMessage(Message.builder().roomId(room.getRoomId()).senderId(account.getEmail()).content("2").build());
        Message message3 = messageService.createMessage(Message.builder().roomId(room.getRoomId()).senderId(account.getEmail()).content("3").build());
        Message message4 = messageService.createMessage(Message.builder().roomId(room.getRoomId()).senderId(account.getEmail()).content("4").build());
        Message message5 = messageService.createMessage(Message.builder().roomId(room.getRoomId()).senderId(account.getEmail()).content("5").build());

        // When & Then
        mockMvc.perform(
                get("/api/v1/rooms/{roomId}/messages", room.getRoomId())
                        .param("baseMessageId", String.valueOf(message4.getMessageId()))
                        .param("size", String.valueOf(2))
                        .param("direction", MessageGetDirection.FORWARD.name())
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messages", hasSize(1)));
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
                .andExpect(jsonPath("$.items[0].subjectId").exists())
                .andExpect(jsonPath("$.items[0].subjectName").exists())
                .andExpect(jsonPath("$.items[0].subjectDescription").exists())
                .andExpect(jsonPath("$.items[0].subjectSubscribeCount").exists())
                .andExpect(jsonPath("$.items[0].roomUserCount").exists())
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

        AccountCreateRequest accountCreateRequest = AccountCreateRequest.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        account = accountService.createAccount(accountCreateRequest);

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