package com.ddd.airplane.chat.message;

class MessageSql {
    static final String SAVE = "INSERT INTO message (room_id, sender_id, content, create_at) VALUES (?, ?, ?, NOW())";
}
