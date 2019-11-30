package com.ddd.airplane.accounts;

class AccountSql {
    static final String FIND_BY_EMAIL = "SELECT email, password, nickname FROM accounts WHERE email = ?";
    static final String SAVE = "INSERT INTO accounts (email, password, nickname) VALUES (?, ?, ?)";
    static final String TRUNCATE = "TRUNCATE TABLE accounts";
}

