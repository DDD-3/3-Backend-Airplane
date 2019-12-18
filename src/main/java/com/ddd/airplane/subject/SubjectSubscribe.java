package com.ddd.airplane.subject;

import lombok.*;

@Getter @Setter @EqualsAndHashCode(of = {"subjectId", "accountId"})
@Builder @NoArgsConstructor @AllArgsConstructor
class SubjectSubscribe {
    private Long subjectId;
    private String accountId;
}
