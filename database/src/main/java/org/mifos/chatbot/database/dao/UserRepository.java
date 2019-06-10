package org.mifos.chatbot.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void findAll() {
        jdbcTemplate.query("SELECT * FROM users_creds", (rs, rowNum) -> null);
    }

}
