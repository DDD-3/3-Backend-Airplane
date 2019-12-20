package com.ddd.airplane.subject.schedule;

class SubjectScheduleSql {
    static final String FIND_BY_SUBJECT_ID =
            "SELECT " +
                "subject_id, " +
                "start_at, " +
                "end_at " +
            "FROM subject_schedule " +
            "WHERE subject_id = ?";
    static final String SAVE = "INSERT INTO subject_schedule (subject_id, start_at, end_at) VALUES (?, ?, ?)";
}
