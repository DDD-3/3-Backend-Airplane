package com.ddd.airplane.subject.like;

import lombok.*;

@Getter @Setter @EqualsAndHashCode(of = {"subjectId", "accountId"})
@Builder @NoArgsConstructor @AllArgsConstructor
class SubjectLike {
    private Long subjectId;
    private String accountId;
}
