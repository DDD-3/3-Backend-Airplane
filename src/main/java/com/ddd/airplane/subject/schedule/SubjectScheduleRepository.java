package com.ddd.airplane.subject.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class SubjectScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<SubjectSchedule> findBySubjectId(Long subjectId) {
        return jdbcTemplate.query(
                SubjectScheduleSql.FIND_BY_SUBJECT_ID,
                new Object[]{subjectId},
                (rs, rowNum) -> SubjectSchedule.builder()
                        .startAt(rs.getTimestamp("start_at").toLocalDateTime())
                        .endAt(rs.getTimestamp("end_at").toLocalDateTime())
                        .build());
    }

    public void save(Long subjectId, LocalDateTime startAt, LocalDateTime endAt) {
        jdbcTemplate.update(SubjectScheduleSql.SAVE, subjectId, startAt, endAt);
    }
}
