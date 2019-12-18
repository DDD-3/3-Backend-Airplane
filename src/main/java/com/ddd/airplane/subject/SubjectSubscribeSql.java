package com.ddd.airplane.subject;

class SubjectSubscribeSql {
    static final String FIND = "SELECT subject_id, account_id FROM subject_subscribe WHERE subject_id = ? AND account_id = ?";
    static final String REPLACE = "REPLACE INTO subject_subscribe (subject_id, account_id) VALUES (?, ?)";
    static final String DELETE = "DELETE FROM subject_subscribe WHERE subject_id = ? AND account_id = ?";
}
