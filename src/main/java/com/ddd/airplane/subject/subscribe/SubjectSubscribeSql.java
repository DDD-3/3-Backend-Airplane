package com.ddd.airplane.subject.subscribe;

class SubjectSubscribeSql {
    static final String FIND = "SELECT subject_id, account_id FROM subject_subscribe WHERE subject_id = ? AND account_id = ?";
    static final String SELECT_SUBSCRIBE_COUNT = "SELECT COUNT(subject_id) AS subscribe_count FROM subject_subscribe WHERE subject_id = ?";
    static final String REPLACE = "REPLACE INTO subject_subscribe (subject_id, account_id) VALUES (?, ?)";
    static final String DELETE = "DELETE FROM subject_subscribe WHERE subject_id = ? AND account_id = ?";
}
