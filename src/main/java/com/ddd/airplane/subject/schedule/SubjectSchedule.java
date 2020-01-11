package com.ddd.airplane.subject.schedule;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class SubjectSchedule {
    private Long startAt;
    private Long endAt;
}
