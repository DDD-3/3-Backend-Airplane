package com.ddd.airplane.subject.like;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SubjectLikeRepository {
    private final JdbcTemplate jdbcTemplate;

    public SubjectLike find(Long subjectId, String accountId) {
        try {
            return jdbcTemplate.queryForObject(
                    SubjectLikeSql.FIND,
                    new Object[]{subjectId, accountId},
                    ((rs, rowNum) -> SubjectLike.builder()
                            .subjectId(rs.getLong("subject_id"))
                            .accountId(rs.getString("account_id"))
                            .build())
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void replace(Long subjectId, String accountId) {
        jdbcTemplate.update(SubjectLikeSql.REPLACE, subjectId, accountId);
    }

    public void delete(Long subjectId, String accountId) {
        jdbcTemplate.update(SubjectLikeSql.DELETE, subjectId, accountId);
    }
}
