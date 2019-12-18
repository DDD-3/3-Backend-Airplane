package com.ddd.airplane.chat.room;

class RoomSql {
    static final String FIND_BY_ID =
            "SELECT " +
                "r.room_id AS room_id, " +
                "s.subject_id AS subject_id, " +
                "s.name AS name, " +
                "s.description AS description " +
            "FROM room r " +
            "INNER JOIN subject s ON r.subject_id = s.subject_id " +
            "WHERE r.room_id = ?";

    static final String SAVE = "INSERT INTO room (subject_id) VALUES (?)";
}
