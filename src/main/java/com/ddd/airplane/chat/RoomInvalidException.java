package com.ddd.airplane.chat;

class RoomInvalidException extends RuntimeException {

    RoomInvalidException(Long roomId) {
        super("Room Invalid : " + roomId);
    }
}
