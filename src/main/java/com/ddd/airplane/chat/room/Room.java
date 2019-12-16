package com.ddd.airplane.chat.room;

import lombok.*;

@Getter @Setter @EqualsAndHashCode(of = "roomId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Room {
    private Long roomId;
    private String name;
}
