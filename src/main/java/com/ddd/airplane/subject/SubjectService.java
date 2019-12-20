package com.ddd.airplane.subject;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.subject.schedule.SubjectSchedule;
import com.ddd.airplane.subject.schedule.SubjectScheduleRepository;
import com.ddd.airplane.subject.subscribe.SubjectSubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubjectService {
    private final SubjectSubscribeRepository subjectSubscribeRepository;
    private final SubjectScheduleRepository subjectScheduleRepository;

    List<SubjectSchedule> getSchedules(Long subjectId) {
        return subjectScheduleRepository.findBySubjectId(subjectId);
    }

    public void addSchedule(Long subjectId, LocalDateTime startAt, LocalDateTime endAt) {
        subjectScheduleRepository.save(subjectId, startAt, endAt);
    }

    boolean subscribed(Long subjectId, Account account) {
        return subjectSubscribeRepository.find(subjectId, account.getEmail()) != null;
    }

    public void subscribe(Long subjectId, Account account) {
        subjectSubscribeRepository.replace(subjectId, account.getEmail());
    }

    public void unsubscribe(Long subjectId, Account account) {
        subjectSubscribeRepository.delete(subjectId, account.getEmail());
    }
}
