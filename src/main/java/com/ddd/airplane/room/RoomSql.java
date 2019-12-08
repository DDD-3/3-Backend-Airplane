package com.ddd.airplane.room;

class RoomSql {
    static final String FIND_BY_ID = "SELECT room_id, name FROM room WHERE room_id = ?";
    static final String SAVE = "INSERT INTO room (name) VALUES (?)";
}
