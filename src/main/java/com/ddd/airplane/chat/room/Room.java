package com.ddd.airplane.chat.room;

import com.ddd.airplane.chat.message.Message;
import com.ddd.airplane.subject.Subject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter @Setter @EqualsAndHashCode(of = "roomId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Room {
    private Long roomId;
    private Subject subject;
    private List<Message> messages;
    private Long userCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean liked;
}
