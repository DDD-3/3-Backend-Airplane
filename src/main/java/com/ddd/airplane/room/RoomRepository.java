package com.ddd.airplane.room;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;

@RequiredArgsConstructor
@Repository
public class RoomRepository {
    private final JdbcTemplate jdbcTemplate;

    Room findById(String roomId) {
        try {
            return jdbcTemplate.queryForObject(
                    RoomSql.FIND_BY_ID,
                    new Object[]{roomId},
                    (rs, rowNum) -> Room.builder()
                    .roomId(rs.getLong("room_id"))
                    .name(rs.getString("name"))
                    .build()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    Room save(String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    RoomSql.SAVE,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            return ps;
        }, keyHolder);

        BigInteger key = (BigInteger) keyHolder.getKey();
        assert key != null;

        return Room.builder()
                .roomId(key.longValue())
                .name(name)
                .build();
    }
}
