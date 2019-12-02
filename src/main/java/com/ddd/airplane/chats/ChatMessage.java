package com.ddd.airplane.chats;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessage {
    private ChatMessageType type;
    private String content;
    private String sender;
}
