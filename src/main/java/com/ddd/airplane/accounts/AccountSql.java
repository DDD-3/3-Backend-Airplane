package com.ddd.airplane.accounts;

class AccountSql {
    static final String FIND_BY_EMAIL = "SELECT email, password, enabled, nickname FROM accounts WHERE email = ?";
    static final String SAVE = "INSERT INTO accounts (email, password, nickname) VALUES (?, ?, ?)";
}
