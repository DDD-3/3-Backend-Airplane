package com.ddd.airplane.subject;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.AccountCreateRequest;
import com.ddd.airplane.account.AccountService;
import com.ddd.airplane.chat.room.Room;
import com.ddd.airplane.chat.room.RoomService;
import com.ddd.airplane.common.AppProperties;
import com.ddd.airplane.common.BaseControllerTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubjectApiControllerTest extends BaseControllerTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private RoomService roomService;
    @Autowired
    private SubjectService subjectService;

    private Account account;
    private String bearerToken;

    @Before
    public void setUp() throws Exception {
        accountService.deleteAll();
        bearerToken = getBearerToken();
    }

    @Test
    public void subscribeSubject() throws Exception {
        // Given
        Room given = roomService.createRoom("주제", "설명");

        // When & Then
        mockMvc.perform(
                post("/api/v1/subjects/{subjectId}/subscribe", given.getSubject().getSubjectId())
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isCreated());

        Assert.assertTrue(subjectService.subscribed(given.getSubject().getSubjectId(), account));
    }

    @Test
    public void unsubscribeSubject() throws Exception {
        // Given
        Room given = roomService.createRoom("주제", "설명");
        subjectService.subscribe(given.getSubject().getSubjectId(), account);

        // When & Then
        mockMvc.perform(
                delete("/api/v1/subjects/{subjectId}/subscribe", given.getSubject().getSubjectId())
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isNoContent());

        Assert.assertFalse(subjectService.subscribed(given.getSubject().getSubjectId(), account));
    }

    @Test
    public void likedSubject() throws Exception {
        // Given
        Room given = roomService.createRoom("주제", "설명");
        subjectService.like(given.getSubject().getSubjectId(), account);

        // When & Then
        mockMvc.perform(
                get("/api/v1/subjects/{subjectId}/like", given.getSubject().getSubjectId())
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(true));

        Assert.assertFalse(subjectService.subscribed(given.getSubject().getSubjectId(), account));
    }

    @Test
    public void likeSubject() throws Exception {
        // Given
        Room given = roomService.createRoom("주제", "설명");

        // When & Then
        mockMvc.perform(
                post("/api/v1/subjects/{subjectId}/like", given.getSubject().getSubjectId())
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isCreated());

        Assert.assertTrue(subjectService.liked(given.getSubject().getSubjectId(), account));
    }

    @Test
    public void dislikeSubject() throws Exception {
        // Given
        Room given = roomService.createRoom("주제", "설명");

        // When & Then
        mockMvc.perform(
                delete("/api/v1/subjects/{subjectId}/like", given.getSubject().getSubjectId())
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        )
                .andDo(print())
                .andExpect(status().isNoContent());

        Assert.assertFalse(subjectService.liked(given.getSubject().getSubjectId(), account));
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
}