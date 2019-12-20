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
            "ORDER BY ss.subscribe_at DESC " +
            "LIMIT ? OFFSET ?";

    static final String SELECT_RECENT_MESSAGED_ROOMS =
            "SELECT " +
                "r.room_id AS room_id, " +
                "r.subject_id AS subject_id, " +
                "(SELECT create_at FROM message WHERE room_id = r.room_id AND sender_id = ? ORDER BY create_at LIMIT 1) AS last_message_at " +
            "FROM room r " +
            "WHERE r.room_id IN " +
                "(SELECT DISTINCT(m.room_id) FROM message m WHERE m.sender_id = ?) " +
            "ORDER BY last_message_at DESC " +
            "LIMIT ? OFFSET ?";

    static final String SAVE = "INSERT INTO room (subject_id) VALUES (?)";
}
