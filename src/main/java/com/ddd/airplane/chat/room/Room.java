package com.ddd.airplane.chat.room;

import com.ddd.airplane.subject.Subject;
import lombok.*;

@Getter @Setter @EqualsAndHashCode(of = "roomId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Room {
    private Long roomId;
    private Subject subject;
    private Long userCount;
}
