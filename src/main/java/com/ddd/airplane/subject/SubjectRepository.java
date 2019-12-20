package com.ddd.airplane.subject;

import com.ddd.airplane.subject.schedule.SubjectSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class SubjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public Subject findById(Long subjectId) {
        try {
            return jdbcTemplate.query(
                    SubjectSql.FIND_BY_ID,
                    new Object[]{subjectId},
                    new ResultSetExtractor<>() {
                        private Set<Long> subjectIds = new HashSet<>();

                        private Long id;
                        private String name;
                        private String description;
                        private List<SubjectSchedule> scheduleList = new ArrayList<>();

                        @Override
                        public Subject extractData(ResultSet rs) throws SQLException {

                            while (rs.next()) {
                                if (subjectIds.contains(id)) {
                                    continue;
                                }
                                subjectIds.add(id);

                                id = rs.getLong("subject_id");
                                name = rs.getString("name");
                                description = rs.getString("description");

                                Timestamp startAt = rs.getTimestamp("start_at");
                                Timestamp endAt = rs.getTimestamp("end_at");
                                if (startAt == null || endAt == null) {
                                    continue;
                                }

                                scheduleList.add(
                                        SubjectSchedule.builder()
                                            .startAt(startAt.toLocalDateTime())
                                            .endAt(endAt.toLocalDateTime())
                                            .build());
                            }

                            return Subject.builder()
                                    .subjectId(id)
                                    .name(name)
                                    .description(description)
                                    .scheduleList(scheduleList)
                                    .build();
                        }
                    }
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
                .scheduleList(List.of())
                .build();
    }
}
