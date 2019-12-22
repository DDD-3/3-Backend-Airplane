package com.ddd.airplane.chat.message;

import lombok.Data;

@Data
public class MessageGetCriteria {
    private Long roomId;
    private Long baseMessageId;
    private Integer size;
    private MessageGetDirection direction;
}
