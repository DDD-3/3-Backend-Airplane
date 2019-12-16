package com.ddd.airplane.chat.message;

import lombok.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class MessagePayload {
    private MessagePayloadType type;
    private Long messageId;
    private Long roomId;
    private String senderId;
    private String senderNickName;
    private String content;
    private Long userCount;
}
