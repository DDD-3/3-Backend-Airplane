package com.ddd.airplane.account;

import com.ddd.airplane.common.AppProperties;
import com.ddd.airplane.common.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.text.MessageFormat;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountApiControllerTest extends BaseControllerTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AppProperties appProperties;

    @Before
    public void setUp() {
        accountService.deleteAll();
    }

    @Test
    public void createAccount() throws Exception {
        AccountCreateRequest accountCreateRequest = AccountCreateRequest.builder()
                .email("y2o2u2n@gmail.com")
                .password("password")
                .nickname("y2o2u2n")
                .build();

        mockMvc.perform(
                post("/api/v1/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(accountCreateRequest))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("email").value("y2o2u2n@gmail.com"))
                .andExpect(jsonPath("password").doesNotExist())
                .andExpect(jsonPath("nickname").value("y2o2u2n"));
    }

    @Test
    public void createAccount_409() throws Exception {
        // Given
        Account registered = generateAccount(409);

        // When & Then
        AccountCreateRequest newAccountCreateRequest = AccountCreateRequest.builder()
                .email(registered.getEmail())
                .password("password")
                .nickname("nickname")
                .build();

        mockMvc.perform(
                post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAccountCreateRequest))
        )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void getAccount() throws Exception {
        // Given
        Account account = generateAccount(100);

        // When &  Then
        mockMvc.perform(
                get("/api/v1/accounts/{email}", account.getEmail())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(account.getEmail()))
                .andExpect(jsonPath("password").doesNotHaveJsonPath())
                .andExpect(jsonPath("nickname").value(account.getNickname()));
    }

    @Test
    public void getAccount_null() throws Exception {
        // Given
        String unknownEmail = "unknown@email.com";

        // When & Then
        this.mockMvc.perform(
                get("/api/v1/accounts/{email}", unknownEmail)
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(nullValue()))
                .andExpect(jsonPath("password").doesNotHaveJsonPath())
                .andExpect(jsonPath("nickname").value(nullValue()));
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

        accountService.createAccount(accountCreateRequest);

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

    private Account generateAccount(int index) {
        AccountCreateRequest accountCreateRequest = AccountCreateRequest.builder()
                .email(MessageFormat.format("sample{0}@email.com", index))
                .password("password")
                .nickname("nickname")
                .build();

        return accountService.createAccount(accountCreateRequest);
    }
}