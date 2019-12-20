package com.ddd.airplane.subject;

class SubjectSql {
    static final String FIND_BY_ID =
            "SELECT " +
                "subject_id, " +
                "name, " +
                "description " +
            "FROM subject " +
            "WHERE subject_id = ? ";

    static final String SAVE = "INSERT INTO subject (name, description) VALUES (?, ?)";
}
