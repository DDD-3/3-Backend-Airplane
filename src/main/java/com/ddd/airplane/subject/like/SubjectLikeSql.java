package com.ddd.airplane.subject.like;

class SubjectLikeSql {
    static final String FIND = "SELECT subject_id, account_id FROM subject_like WHERE subject_id = ? AND account_id = ?";
    static final String REPLACE = "REPLACE INTO subject_like (subject_id, account_id, like_at) VALUES (?, ?, NOW())";
    static final String DELETE = "DELETE FROM subject_like WHERE subject_id = ? AND account_id = ?";
}
