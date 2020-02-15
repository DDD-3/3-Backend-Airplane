package com.ddd.airplane.account;

import com.ddd.airplane.common.BaseServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceTest extends BaseServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        accountService.deleteAll();;
    }

    @Test
    public void loadUserByUsername() {
        // Given
        String email = "y2o2u2n@gmail.com";
        String password = "password";
        String nickname = "semistone222";

        AccountCreateRequest accountCreateRequest = AccountCreateRequest.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        accountService.createAccount(accountCreateRequest);

        // When
        UserDetails userDetails = accountService.loadUserByUsername(email);

        // Then
        assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_notFound() {
        String email = "unknown@gmail.com";
        accountService.loadUserByUsername(email);
    }
}