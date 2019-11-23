package com.ddd.airplane.accounts;

import com.ddd.airplane.common.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
