package com.ddd.airplane.account;

class AccountSql {
    static final String FIND_BY_EMAIL = "SELECT email, password, nickname FROM account WHERE email = ?";
    static final String SAVE = "INSERT INTO account (email, password, nickname) VALUES (?, ?, ?)";
    static final String TRUNCATE = "TRUNCATE TABLE account";
}

