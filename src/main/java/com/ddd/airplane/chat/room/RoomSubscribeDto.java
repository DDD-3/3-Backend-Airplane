package com.ddd.airplane.chat.room;

import com.ddd.airplane.subject.Subject;
import lombok.Data;

@Data
public class RoomSubscribeDto {
    private Long roomId;
    private Long subjectId;
    private String subjectName;
    private String subjectDescription;
    private Long subjectSubscribeCount;
    private Long roomUserCount;

    public RoomSubscribeDto(Room room) {
        this.roomId = room.getRoomId();
        Subject subject = room.getSubject();
        this.subjectId = subject.getSubjectId();
        this.subjectName = subject.getName();
        this.subjectDescription = subject.getDescription();
        this.subjectSubscribeCount = subject.getSubscribeCount();
        this.roomUserCount = room.getUserCount();
    }
}
