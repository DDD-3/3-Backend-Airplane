package com.ddd.airplane.subject;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.CurrentAccount;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SubjectApiController {
    // TODO : 주제 구독하기
    @PostMapping("/v1/subjects/{subjectId}/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribeSubject(
            @PathVariable Long subjectId,
            @CurrentAccount Account account
    ) {
        //
    }

    // TODO : 주제 구독 취소하기
    @DeleteMapping("/v1/subjects/{subjectId}/subscribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribeSubject(
            @PathVariable Long subjectId,
            @CurrentAccount Account account
    ) {
        //
    }
}
