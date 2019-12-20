package com.ddd.airplane.chat.room;

import com.ddd.airplane.common.PageInfo;
import com.ddd.airplane.subject.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class RoomRepository {
    private static final String USER_COUNT_KEY = "ROOM:{0}:USER_COUNT";

    private final JdbcTemplate jdbcTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> userCountValueOperations;

    Room findById(Long roomId) {
        try {
            return jdbcTemplate.queryForObject(
                    RoomSql.FIND_BY_ID,
                    new Object[]{roomId},
                    (rs, rowNum) -> Room.builder()
                    .roomId(rs.getLong("room_id"))
                    .subject(
                            Subject.builder()
                                    .subjectId(rs.getLong("subject_id"))
                                    .build()
                    )
                    .userCount(getUserCount(roomId))
                    .build()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    List<Room> selectSubscribedRooms(String accountId, PageInfo pageInfo) {
        try {
            return jdbcTemplate.query(
                    RoomSql.SELECT_SUBSCRIBED_ROOMS,
                    new Object[]{accountId, pageInfo.getLimit(), pageInfo.getOffset()},
                    (rs, rowNum) -> {
                        Long  roomId = rs.getLong("room_id");

                        return Room.builder()
                                .roomId(roomId)
                                .subject(
                                        Subject.builder()
                                                .subjectId(rs.getLong("subject_id"))
                                                .build()
                                )
                                .userCount(getUserCount(roomId))
                                .build();
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    List<Room> selectRecentMessagedRooms(String accountId, PageInfo pageInfo) {
        try {
            return jdbcTemplate.query(
                    RoomSql.SELECT_RECENT_MESSAGED_ROOMS,
                    new Object[]{accountId, accountId, pageInfo.getLimit(), pageInfo.getOffset()},
                    (rs, rowNum) -> {
                        Long  roomId = rs.getLong("room_id");

                        return Room.builder()
                                .roomId(roomId)
                                .subject(
                                        Subject.builder()
                                                .subjectId(rs.getLong("subject_id"))
                                                .build()
                                )
                                .userCount(getUserCount(roomId))
                                .build();
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    Room save(Subject subject) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    RoomSql.SAVE,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, subject.getSubjectId());
            return ps;
        }, keyHolder);


        Number generatedKey = keyHolder.getKey();
        assert generatedKey != null;

        return Room.builder()
                .roomId(generatedKey.longValue())
                .userCount(0L)
                .build();
    }

    private Long getUserCount(Long roomId) {
        String key = MessageFormat.format(USER_COUNT_KEY, roomId);
        String userCount = Optional.ofNullable(userCountValueOperations.get(key)).orElse("0");
        return Long.valueOf(userCount);
    }

    Long incrementUserCount(Long roomId) {
        String key = MessageFormat.format(USER_COUNT_KEY, roomId);
        return userCountValueOperations.increment(key);
    }

    Long decrementUserCount(Long roomId) {
        String key = MessageFormat.format(USER_COUNT_KEY, roomId);
        return userCountValueOperations.decrement(key);
    }
}
