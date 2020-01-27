package com.ddd.airplane.chat.room;

import com.ddd.airplane.chat.message.Message;
import com.ddd.airplane.chat.message.MessageDto;
import com.ddd.airplane.subject.Subject;
import com.ddd.airplane.subject.schedule.SubjectSchedule;
import com.ddd.airplane.subject.schedule.SubjectScheduleDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoomDetailDto {
    private Long roomId;
    private Long subjectId;
    private String subjectName;
    private String subjectDescription;
    private SubjectScheduleDto upcomingSubjectSchedule;
    private Long subjectSubscribeCount;
    private Boolean subjectSubscribed;
    private List<MessageDto> recentMessages;
    private Long roomUserCount;
    private Boolean roomLiked;

    public RoomDetailDto(Room room) {
        this.roomId = room.getRoomId();
        Subject subject = room.getSubject();
        this.subjectId = subject.getSubjectId();
        this.subjectName = subject.getName();
        this.subjectDescription = subject.getDescription();
        List<SubjectSchedule> scheduleList = subject.getScheduleList();
        if (scheduleList != null && !scheduleList.isEmpty()) {
            this.upcomingSubjectSchedule = new SubjectScheduleDto(scheduleList.get(0));
        }
        this.subjectSubscribeCount = subject.getSubscribeCount();
        this.subjectSubscribed = subject.getSubscribed();
        List<Message> messages = room.getMessages();
        if (messages != null && !messages.isEmpty()) {
            this.recentMessages = messages.stream()
                    .map(MessageDto::new)
                    .collect(Collectors.toList());
        }
        this.roomUserCount = room.getUserCount();
        this.roomLiked = room.getLiked();
    }
}
