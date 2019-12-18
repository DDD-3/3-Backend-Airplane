package com.ddd.airplane.subject;

import lombok.*;

@Getter @Setter @EqualsAndHashCode(of = "subjectId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Subject {
    private Long subjectId;
    private String name;
    private String description;
}
