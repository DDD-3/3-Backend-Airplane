package com.ddd.airplane.subject;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.CurrentAccount;
import com.ddd.airplane.common.BooleanResponse;
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

    @GetMapping("/v1/subjects/{subjectId}/like")
    @ResponseStatus(HttpStatus.OK)
    public BooleanResponse likedSubject(
            @PathVariable Long subjectId,
            @CurrentAccount Account account
    ) {
        boolean liked = subjectService.liked(subjectId, account);
        return new BooleanResponse(liked);
    }

    @PostMapping("/v1/subjects/{subjectId}/like")
    @ResponseStatus(HttpStatus.CREATED)
    public void likeSubject(
            @PathVariable Long subjectId,
            @CurrentAccount Account account
    ) {
        subjectService.like(subjectId, account);
    }

    @DeleteMapping("/v1/subjects/{subjectId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dislikeSubject(
            @PathVariable Long subjectId,
            @CurrentAccount Account account
    ) {
        subjectService.dislike(subjectId, account);
    }
}
