package com.ddd.airplane.chat.message;

import lombok.Data;

@Data
public class MessageDto {
    private Long messageId;
    private Long roomId;
    private String senderId;
    private String content;
    private Long createAt;

    public MessageDto(Message message) {
        this.messageId = message.getMessageId();
        this.roomId = message.getRoomId();
        this.senderId = message.getSenderId();
        this.content = message.getContent();
        this.createAt = message.getCreateAt();
    }
}
