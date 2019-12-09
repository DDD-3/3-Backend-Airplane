package com.ddd.airplane.chat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
class ChatMessage {
    private ChatMessageType type;
    private Long roomId;
    private String senderId;
    private String content;
}
