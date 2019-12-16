package com.ddd.airplane.chat.room;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(Long roomId) {
        super("Room Not Found : " + roomId);
    }
}
