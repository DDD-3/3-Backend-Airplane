package com.ddd.airplane.account;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accessToken) {
        super("Account Not Found : " + accessToken);
    }
}
