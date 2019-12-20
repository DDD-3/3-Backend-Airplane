package com.ddd.airplane.chat.room;

class RoomSql {
    static final String FIND_BY_ID =
            "SELECT " +
                "room_id, " +
                "subject_id " +
            "FROM room " +
            "WHERE room_id = ?";

    static final String SAVE = "INSERT INTO room (subject_id) VALUES (?)";
}
