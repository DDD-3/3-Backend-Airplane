package com.ddd.airplane.chat.room;

public class RoomInvalidException extends RuntimeException {

    public RoomInvalidException(Long roomId) {
        super("Room Invalid : " + roomId);
    }
}
