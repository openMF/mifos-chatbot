package org.mifos.chatbot.database.dao;

import org.mifos.chatbot.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM users_creds",
                (rs, rowNum) -> new User(rs.getString("Username"),
                        rs.getString("secret_Pass"), rs.getString("FB_userID"), rs.getString("slack_userID"), rs.getString("telegram_userID"), rs.getString("skype_userID"))
        );
    }

    public User findUserByUsername(String username) {
        List<User> l = jdbcTemplate.query(
                "SELECT * FROM users_creds WHERE Username = '" + username + "'",
                (rs, rowNum) -> new User(
                        rs.getString("Username"),
                        rs.getString("secret_Pass"),
                        rs.getString("FB_userID"),
                        rs.getString("slack_userID"),
                        rs.getString("telegram_userID"),
                        rs.getString("skype_userID"))
        );
        try {
            return l.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public User findUserByFBID(String FB_userID) {
        List<User> l = jdbcTemplate.query(
                "SELECT * FROM users_creds WHERE FB_userID = '" + FB_userID + "'",
                (rs, rowNum) -> new User(
                        rs.getString("Username"),
                        rs.getString("secret_Pass"),
                        rs.getString("FB_userID"),
                        rs.getString("slack_userID"),
                        rs.getString("telegram_userID"),
                        rs.getString("skype_userID"))
        );
        try {
            return l.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public User findUserByTelegramID(String telegram_userID) {
        List<User> l = jdbcTemplate.query(
                "SELECT * FROM users_creds WHERE telegram_userID = '" + telegram_userID + "'",
                (rs, rowNum) -> new User(
                        rs.getString("Username"),
                        rs.getString("secret_Pass"),
                        rs.getString("FB_userID"),
                        rs.getString("slack_userID"),
                        rs.getString("telegram_userID"),
                        rs.getString("skype_userID"))
        );
        try {
            return l.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public void addUser(String username, String secret_Pass, String FB_userID, String slack_userID, String telegram_userID, String skype_userID) {
        jdbcTemplate.update("INSERT INTO users_creds VALUES (?,?,?,?,?,?)",
                username, secret_Pass, FB_userID,slack_userID,telegram_userID,skype_userID);
    }

    public void addUserByFBID(String username, String secret_Pass, String FB_userID) {
        jdbcTemplate.update("INSERT INTO users_creds (FB_userID, Username, secret_Pass) VALUES (?,?,?)",
                FB_userID, username, secret_Pass);
    }

    public void addUserByTelegramID(String username, String secret_Pass, String telegram_userID) {
        jdbcTemplate.update("INSERT INTO users_creds (telegram_userID, Username, secret_Pass) VALUES (?,?,?)",
                telegram_userID, username, secret_Pass);
    }

    public void updateUserFBID(String username, String secret_Pass, String FB_userID) {
        jdbcTemplate.update("UPDATE users_creds SET FB_userID = ? WHERE Username = ? AND secret_Pass = ?",
                FB_userID, username, secret_Pass);
    }

    public void updateUserTelegramID(String username, String secret_Pass, String telegram_userID) {
        jdbcTemplate.update("UPDATE users_creds SET telegram_userID = ? WHERE Username = ? AND secret_Pass = ?",
                telegram_userID, username, secret_Pass);
    }

    public void removeUser(String username) {
        jdbcTemplate.update("DELETE FROM users_creds WHERE Username = '" + username + "'");
    }

    public boolean FBIDExist(String senderId) {
        User user = findUserByFBID(senderId);
        return user != null;
    }

    public boolean TelegramIDExist(String senderId) {
        User user = findUserByTelegramID(senderId);
        return user != null;
    }
}
