package com.ddd.airplane.subject.subscribe;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SubjectSubscribeRepository {
    private final JdbcTemplate jdbcTemplate;

    public SubjectSubscribe find(Long subjectId, String accountId) {
        try {
            return jdbcTemplate.queryForObject(
                    SubjectSubscribeSql.FIND,
                    new Object[]{subjectId, accountId},
                    ((rs, rowNum) -> SubjectSubscribe.builder()
                            .subjectId(rs.getLong("subject_id"))
                            .accountId(rs.getString("account_id"))
                            .build())
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Long selectSubScribeCount(Long subjectId) {
        return jdbcTemplate.queryForObject(
                SubjectSubscribeSql.SELECT_SUBSCRIBE_COUNT,
                new Object[]{subjectId},
                ((rs, rowNum) -> rs.getLong("subscribe_count"))
        );
    }

    public void replace(Long subjectId, String accountId) {
        jdbcTemplate.update(SubjectSubscribeSql.REPLACE, subjectId, accountId);
    }

    public void delete(Long subjectId, String accountId) {
        jdbcTemplate.update(SubjectSubscribeSql.DELETE, subjectId, accountId);
    }
}
