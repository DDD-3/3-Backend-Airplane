package com.ddd.airplane.chat;

class RoomNotFoundException extends RuntimeException {
    RoomNotFoundException(Long roomId) {
        super("Room Not Found : " + roomId);
    }
}
