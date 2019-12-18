package com.ddd.airplane.subject;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@RequiredArgsConstructor
@Repository
public class SubjectRepository {
    private final JdbcTemplate jdbcTemplate;

    Subject findById(Long subjectId) {
        try {
            return jdbcTemplate.queryForObject(
                    SubjectSql.FIND_BY_ID,
                    new Object[]{subjectId},
                    ((rs, rowNum) -> Subject.builder()
                            .subjectId(rs.getLong("subject_id"))
                            .name(rs.getString("name"))
                            .description(rs.getString("description"))
                            .build())
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Subject save(Subject subject) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    SubjectSql.SAVE,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subject.getName());
            ps.setString(2, subject.getDescription());
            return ps;
        }, keyHolder);

        Number generatedKey = keyHolder.getKey();
        assert generatedKey != null;

        return Subject.builder()
                .subjectId(generatedKey.longValue())
                .name(subject.getName())
                .description(subject.getDescription())
                .build();
    }
}
