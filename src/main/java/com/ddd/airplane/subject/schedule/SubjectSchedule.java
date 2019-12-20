package com.ddd.airplane.subject.schedule;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class SubjectSchedule {
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
