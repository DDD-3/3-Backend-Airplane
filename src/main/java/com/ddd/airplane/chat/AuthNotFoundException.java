package com.ddd.airplane.chat;

class AuthNotFoundException extends RuntimeException {
    AuthNotFoundException(String accessToken) {
        super("Auth Not Found : " + accessToken);
    }
}
