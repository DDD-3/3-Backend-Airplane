package com.ddd.airplane.subject.subscribe;

import lombok.*;

@Getter @Setter @EqualsAndHashCode(of = {"subjectId", "accountId"})
@Builder @NoArgsConstructor @AllArgsConstructor
class SubjectSubscribe {
    private Long subjectId;
    private String accountId;
}
