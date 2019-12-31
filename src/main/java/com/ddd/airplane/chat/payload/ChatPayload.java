package com.ddd.airplane.chat.payload;

import lombok.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class ChatPayload {
    private ChatPayloadType type;
    private Long messageId;
    private Long roomId;
    private String senderId;
    private String senderNickName;
    private String content;
    private Long userCount;
}
