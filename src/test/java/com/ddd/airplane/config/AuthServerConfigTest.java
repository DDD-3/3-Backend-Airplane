package com.ddd.airplane.config;

import com.ddd.airplane.accounts.AccountDto;
import com.ddd.airplane.accounts.AccountService;
import com.ddd.airplane.common.AppProperties;
import com.ddd.airplane.common.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AppProperties appProperties;

    @Before
    public void setUp() {
        accountService.deleteAll();
    }

    @Test
    public void getAuthToken() throws Exception {
        // Given
        String email = "y2o2u2n@gmail.com";
        String password = "password";
        String nickname = "y2o2u2n";

        AccountDto accountDto = AccountDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        accountService.createAccount(accountDto);

        mockMvc.perform(
                post("/oauth/token")
                        .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                        .param("username", email)
                        .param("password", password)
                        .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }

    @Test
    public void getAuthToken_using_refreshToken() throws Exception {
        String refreshToken = getRefreshToken();

        mockMvc.perform(
                post("/oauth/token")
                        .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                        .param("grant_type", "refresh_token")
                        .param("refresh_token", refreshToken)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(jsonPath("refresh_token").value(refreshToken));
    }

    private String getRefreshToken() throws Exception {
        String email = "sample@gmail.com";
        String password = "password";
        String nickname = "sample";

        AccountDto accountDto = AccountDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        accountService.createAccount(accountDto);

        ResultActions perform = mockMvc.perform(
                post("/oauth/token")
                        .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                        .param("username", email)
                        .param("password", password)
                        .param("grant_type", "password")
        );

        String responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("refresh_token").toString();
    }
}