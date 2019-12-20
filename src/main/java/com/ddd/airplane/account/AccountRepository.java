package com.ddd.airplane.account;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@AllArgsConstructor
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
                            .nickname(rs.getString("nickname"))
                            .roles(Set.of(AccountRole.ROLE_USER))
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

    void truncate() {
        jdbcTemplate.update(
                AccountSql.TRUNCATE
        );
    }
}
