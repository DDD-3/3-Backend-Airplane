package com.ddd.airplane.accounts;

import com.ddd.airplane.common.AlreadyRegisteredException;

class AccountAlreadyRegisteredException extends AlreadyRegisteredException {
    AccountAlreadyRegisteredException(String message) {
        super(message);
    }
}
