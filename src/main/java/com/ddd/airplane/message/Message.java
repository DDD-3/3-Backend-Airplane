package com.ddd.airplane.message;

import lombok.*;

import java.util.Date;

@Getter @Setter @EqualsAndHashCode(of = "messageId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Message {
    private Long messageId;
    private Long roomId;
    private String senderId;
    private String content;
    private Date createdAt;
}
