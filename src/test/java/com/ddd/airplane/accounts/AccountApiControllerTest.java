package com.ddd.airplane.accounts;

import com.ddd.airplane.common.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountApiControllerTest extends BaseControllerTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void signUp() throws Exception {
        AccountDto accountDto = AccountDto.builder()
                .email("y2o2u2n@gmail.com")
                .password("password")
                .nickname("y2o2u2n")
                .build();

        mockMvc.perform(
                post("/api/v1/signUp")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(accountDto))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("email").value("y2o2u2n@gmail.com"))
                .andExpect(jsonPath("password").doesNotExist())
                .andExpect(jsonPath("nickname").value("y2o2u2n"));
    }

    @Test
    void signUp_409() throws Exception {
        // Given
        Account registered = generateAccount(409);

        // When & Then
        AccountDto newAccountDto = AccountDto.builder()
                .email(registered.getEmail())
                .password("password")
                .nickname("nickname")
                .build();

        mockMvc.perform(
                post("/api/v1/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAccountDto))
        )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void getAccount() throws Exception {
        // Given
        Account account = generateAccount(100);

        // When &  Then
        mockMvc.perform(get("/api/v1/accounts/{email}", account.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(account.getEmail()))
                .andExpect(jsonPath("password").doesNotExist())
                .andExpect(jsonPath("nickname").value(account.getNickname()));
    }

    @Test
    void getAccount_404() throws Exception {
        // Given
        String unknownEmail = "unknown@email.com";

        // When & Then
        this.mockMvc.perform(get("/api/v1/accounts/{email}", unknownEmail))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private Account generateAccount(int index) {
        Account account = Account.builder()
                .email(MessageFormat.format("sample{0}@email.com", index))
                .password("password")
                .nickname("nickname")
                .build();

        return accountRepository.save(account);
    }
}