package com.ddd.airplane.chat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessage {
    private ChatMessageType type;
    private String content;
    private String sender;
    private Long roomId;
}
