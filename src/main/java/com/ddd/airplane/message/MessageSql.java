package com.ddd.airplane.message;

class MessageSql {
    static final String SAVE = "INSERT INTO message (room_id, sender_id, content, created_at) VALUES (?, ?, ?, NOW())";
}
