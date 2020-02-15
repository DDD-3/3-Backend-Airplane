package com.ddd.airplane.chat.room;

import com.ddd.airplane.subject.Subject;
import lombok.Data;

@Data
public class RoomDto {
    private Long roomId;
    private String subjectName;
    private String subjectDescription;
    private String subjectThumbnailUrl;
    private Long subjectSubscribeCount;
    private Long roomUserCount;

    public RoomDto(Room room) {
        this.roomId = room.getRoomId();
        Subject subject = room.getSubject();
        this.subjectName = subject.getName();
        this.subjectDescription = subject.getDescription();
        this.subjectThumbnailUrl = null;
        if (subject.getSubjectId() == 1L) {
            this.subjectThumbnailUrl = "https://s3.amazonaws.com/thumbnail.airplane/img_sumbnail_mello.jpg";
        } else if (subject.getSubjectId() == 2L) {
            this.subjectThumbnailUrl = "https://s3.amazonaws.com/thumbnail.airplane/img_sumbnail_zoo.jpg";
        }

        this.subjectSubscribeCount = subject.getSubscribeCount();
        this.roomUserCount =room.getUserCount();
    }
}
