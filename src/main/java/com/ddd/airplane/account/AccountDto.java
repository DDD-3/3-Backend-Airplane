package com.ddd.airplane.account;

import lombok.Data;

@Data
public class AccountDto {
    private String email;
    private String nickname;

    public AccountDto(Account account) {
        this.email = account.getEmail();
        this.nickname = account.getNickname();
    }
}
