package com.ddd.airplane.chat.room;

import lombok.Data;

@Data
public class RoomDto {
    private Long roomId;
    private String subjectName;
    private String subjectDescription;
    private String subjectImageUrl;
    private Long subjectSubscribeCount;
    private Long roomUserCount;

    public RoomDto(Long roomId, String subjectName, String subjectDescription, String subjectImageUrl, Long subjectSubscribeCount, Long roomUserCount) {
        this.roomId = roomId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.subjectImageUrl = subjectImageUrl;
        this.subjectSubscribeCount = subjectSubscribeCount;
        this.roomUserCount = roomUserCount;
    }
}
