package com.ddd.airplane.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyRegisteredException extends RuntimeException {
    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
