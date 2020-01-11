package com.ddd.airplane.subject;

import com.ddd.airplane.subject.schedule.SubjectSchedule;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter @Setter @EqualsAndHashCode(of = "subjectId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Subject {
    private Long subjectId;
    private String name;
    private String description;
    private List<SubjectSchedule> scheduleList;
    private Long subscribeCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean subscribed;
}
