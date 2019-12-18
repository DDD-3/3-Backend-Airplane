package com.ddd.airplane.subject;

import com.ddd.airplane.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubjectService {
    private final SubjectSubscribeRepository subjectSubscribeRepository;

    boolean subscribed(Long subjectId, Account account) {
        return subjectSubscribeRepository.find(subjectId, account.getEmail()) != null;
    }

    void subscribe(Long subjectId, Account account) {
        subjectSubscribeRepository.replace(subjectId, account.getEmail());
    }

    void unsubscribe(Long subjectId, Account account) {
        subjectSubscribeRepository.delete(subjectId, account.getEmail());
    }
}
