package com.ddd.airplane.subject;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.CurrentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SubjectApiController {
    private final SubjectService subjectService;

    @PostMapping("/v1/subjects/{subjectId}/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribeSubject(
            @PathVariable Long subjectId,
            @CurrentAccount Account account
    ) {
        subjectService.subscribe(subjectId, account);
    }

    @DeleteMapping("/v1/subjects/{subjectId}/subscribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribeSubject(
            @PathVariable Long subjectId,
            @CurrentAccount Account account
    ) {
        subjectService.unsubscribe(subjectId, account);
    }
}
