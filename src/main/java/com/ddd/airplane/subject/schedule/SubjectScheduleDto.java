package com.ddd.airplane.subject.schedule;

import lombok.Data;

@Data
public class SubjectScheduleDto {
    private Long startAt;
    private Long endAt;

    public SubjectScheduleDto(SubjectSchedule schedule) {
        this.startAt = schedule.getStartAt();
        this.endAt = schedule.getEndAt();
    }
}
