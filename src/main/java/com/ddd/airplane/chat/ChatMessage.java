package com.ddd.airplane.chat;

import lombok.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
class ChatMessage {
    private ChatMessageType type;
    private Long roomId;
    private String senderId;
    private String senderNickName;
    private String content;
}
