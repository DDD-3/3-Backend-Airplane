package com.ddd.airplane.chat;

class RoomNotFoundException extends RuntimeException {
    RoomNotFoundException(String message) {
        super("Room Not Found : " + message);
    }
}
