package com.ddd.airplane.chat;

import lombok.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
class ChatMessage {
    private ChatMessageType type;
    private Long roomId;
    private String senderId;
    private String content;

    ChatMessage(ChatMessageType type, Long roomId, String senderId) {
        this.type = type;
        this.roomId = roomId;
        this.senderId = senderId;
    }
}
