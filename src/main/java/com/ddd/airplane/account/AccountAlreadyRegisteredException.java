package com.ddd.airplane.account;

import com.ddd.airplane.common.AlreadyRegisteredException;

class AccountAlreadyRegisteredException extends AlreadyRegisteredException {
    AccountAlreadyRegisteredException(String message) {
        super(message);
    }
}
