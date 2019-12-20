package com.ddd.airplane.chat.room;

class RoomSql {
    static final String FIND_BY_ID =
            "SELECT " +
                "room_id, " +
                "subject_id " +
            "FROM room " +
            "WHERE room_id = ?";

    static final String SELECT_SUBSCRIBED_ROOMS =
            "SELECT " +
                "r.room_id, " +
                "r.subject_id " +
            "FROM room r " +
            "INNER JOIN subject_subscribe ss ON r.subject_id = ss.subject_id " +
            "WHERE ss.account_id = ? " +
            "ORDER BY ss.subscribe_at DESC";

    static final String SAVE = "INSERT INTO room (subject_id) VALUES (?)";
}
