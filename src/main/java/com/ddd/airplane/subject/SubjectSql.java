package com.ddd.airplane.subject;

class SubjectSql {
    static final String FIND_BY_ID =
            "SELECT " +
                "s.subject_id, " +
                "s.name, " +
                "s.description, " +
                "ss.start_at, " +
                "ss.end_at " +
            "FROM subject s " +
            "LEFT OUTER JOIN subject_schedule ss ON s.subject_id = ss.subject_id " +
            "WHERE s.subject_id = ? " +
            "ORDER BY ss.start_at ASC";

    static final String SAVE = "INSERT INTO subject (name, description) VALUES (?, ?)";
}
