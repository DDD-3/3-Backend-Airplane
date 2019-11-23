package com.ddd.airplane.accounts;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;

    Account findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT email, nickname FROM accounts WHERE email = ?",
                    new Object[]{email},
                    (rs, rowNum) -> Account.builder()
                            .email(rs.getString("email"))
                            .nickname(rs.getString("nickname"))
                            .build()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    Account save(Account account) {
        jdbcTemplate.update(
                "INSERT INTO accounts (email, password, nickname) VALUES (?, ?, ?)",
                account.getEmail(),
                account.getPassword(),
                account.getNickname()
        );

        return Account.builder()
                .email(account.getEmail())
                .nickname(account.getNickname())
                .build();
    }

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
