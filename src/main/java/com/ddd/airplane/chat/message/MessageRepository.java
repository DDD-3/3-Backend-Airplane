package com.ddd.airplane.chat.message;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MessageRepository {
    private final JdbcTemplate jdbcTemplate;

    List<Message> selectRecentMessagesInRoom(Long roomId, int size) {
        try {
            return jdbcTemplate.query(
                    MessageSql.SELECT_RECENT_MESSAGES_IN_ROOM,
                    new Object[]{roomId, size},
                    (rs, rowNum) -> Message.builder()
                            .messageId(rs.getLong("message_id"))
                            .roomId(rs.getLong("room_id"))
                            .senderId(rs.getString("sender_id"))
                            .content(rs.getString("content"))
                            .createAt(rs.getTimestamp("create_at").getTime())
                            .build());
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    List<Message> selectMessagesInRoom(MessageGetCriteria criteria) {

        try {
            return jdbcTemplate.query(
                    criteria.getDirection() == MessageGetDirection.BACKWARD
                            ? MessageSql.SELECT_MESSAGES_IN_ROOM_BACKWARD
                            : MessageSql.SELECT_MESSAGES_IN_ROOM_FORWARD,
                    new Object[]{criteria.getRoomId(), criteria.getBaseMessageId(), criteria.getSize()},
                    (rs, rowNum) -> {
                        return Message.builder()
                                .messageId(rs.getLong("message_id"))
                                .roomId(rs.getLong("room_id"))
                                .senderId(rs.getString("sender_id"))
                                .content(rs.getString("content"))
                                .createAt(rs.getTimestamp("create_at").getTime())
                                .build();
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    Message save(Message message) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    MessageSql.SAVE,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, message.getRoomId());
            ps.setString(2, message.getSenderId());
            ps.setString(3, message.getContent());
            return ps;
        }, keyHolder);

        Number generatedKey = keyHolder.getKey();
        assert generatedKey != null;

        return Message.builder()
                .messageId(generatedKey.longValue())
                .roomId(message.getRoomId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .createAt(Instant.now().toEpochMilli())
                .build();
    }
}
