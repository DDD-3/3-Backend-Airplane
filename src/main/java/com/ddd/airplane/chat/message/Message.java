package com.ddd.airplane.chat.message;

import lombok.*;

@Getter @Setter @EqualsAndHashCode(of = "messageId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Message {
    private Long messageId;
    private Long roomId;
    private String senderId;
    private String content;
    private Long createAt;
}
