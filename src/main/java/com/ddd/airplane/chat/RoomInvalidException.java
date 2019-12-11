package com.ddd.airplane.chat;

class RoomInvalidException extends RuntimeException {

    RoomInvalidException(String message) {
        super("Room Invalid : " + message);
    }
}
