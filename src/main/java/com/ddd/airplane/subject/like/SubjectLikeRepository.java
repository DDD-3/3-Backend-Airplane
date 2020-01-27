package com.ddd.airplane.subject.like;

import com.ddd.airplane.common.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public List<Long> findLikedSubjectIdList(PageInfo pageInfo) {
        return jdbcTemplate.query(
                SubjectLikeSql.FIND_LIKED_SUBJECT_ID_LIST,
                new Object[]{pageInfo.getLimit(), pageInfo.getOffset()},
                (rs, rowNum) -> rs.getLong("subject_id"));
    }

    public void replace(Long subjectId, String accountId) {
        jdbcTemplate.update(SubjectLikeSql.REPLACE, subjectId, accountId);
    }

    public void delete(Long subjectId, String accountId) {
        jdbcTemplate.update(SubjectLikeSql.DELETE, subjectId, accountId);
    }
}
