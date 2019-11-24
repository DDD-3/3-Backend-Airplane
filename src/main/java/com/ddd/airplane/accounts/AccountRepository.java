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
                    AccountSql.FIND_BY_EMAIL,
                    new Object[]{email},
                    (rs, rowNum) -> Account.builder()
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .enabled(rs.getBoolean("enabled"))
                            .nickname(rs.getString("nickname"))
                            .build()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    Account save(Account account) {
        jdbcTemplate.update(
                AccountSql.SAVE,
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
