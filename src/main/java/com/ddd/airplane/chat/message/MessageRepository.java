package com.ddd.airplane.chat.message;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;

@RequiredArgsConstructor
@Repository
public class MessageRepository {
    private final JdbcTemplate jdbcTemplate;

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
                .createAt(new Date())
                .build();
    }
}
