package com.ddd.airplane.chat.message;

class MessageSql {
    static final String SAVE = "INSERT INTO message (room_id, sender_id, content, create_at) VALUES (?, ?, ?, NOW())";
    static final String SELECT_MESSAGES_IN_ROOM_BACKWARD =
            "SELECT " +
                "sub.message_id, " +
                "sub.room_id, " +
                "sub.sender_id, " +
                "sub.content, " +
                "sub.create_at " +
            "FROM " +
                "( " +
                    "SELECT " +
                        "message_id, " +
                        "room_id, " +
                        "sender_id, " +
                        "content, " +
                        "create_at " +
                    "FROM message " +
                    "WHERE room_id = ? " +
                        "AND message_id < ? " +
                    "ORDER BY message_id DESC " +
                    "LIMIT ? " +
                ") sub " +
            "ORDER BY sub.message_id ASC";

    static final String SELECT_MESSAGES_IN_ROOM_FORWARD =
            "SELECT " +
                "message_id, " +
                "room_id, " +
                "sender_id, " +
                "content, " +
                "create_at " +
            "FROM message " +
            "WHERE room_id = ? " +
                "AND message_id > ? " +
            "ORDER BY message_id ASC " +
            "LIMIT ?";
}
