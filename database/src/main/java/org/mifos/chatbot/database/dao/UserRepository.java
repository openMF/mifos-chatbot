package org.mifos.chatbot.database.dao;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class UserRepository {

    private String tableName = "users_creds";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        final String findAllQuery = "SELECT * FROM " + tableName;
        return jdbcTemplate.query(
                findAllQuery,
                (rs, rowNum) -> new User(rs.getString("username"),
                        rs.getString("secret_key"), rs.getString("fb_userid"), rs.getString("slack_userid"), rs.getString("telegram_userid"), rs.getString("skype_userid"))
        );
    }

    public User findUserByUsername(String username) {
        final String findUserByUsernameQuery = "SELECT * FROM " + tableName + " WHERE username = '" + username + "'";
        List<User> users = jdbcTemplate.query(
                findUserByUsernameQuery,
                (rs, rowNum) -> new User(
                        rs.getString("username"),
                        rs.getString("secret_key"),
                        rs.getString("fb_userid"),
                        rs.getString("slack_userid"),
                        rs.getString("telegram_userid"),
                        rs.getString("skype_userid"))
        );
        try {
            return users.get(0);
        } catch (Exception e) {
            log.info("User with username: " + username + " not found.");
            return null;
        }
    }

    public User findUserByFBID(String fbUserid) {
        final String findUserByFBIDQuery = "SELECT * FROM " + tableName + " WHERE fb_userid = '" + fbUserid + "'";
        List<User> users = jdbcTemplate.query(
                findUserByFBIDQuery,
                (rs, rowNum) -> new User(
                        rs.getString("username"),
                        rs.getString("secret_key"),
                        rs.getString("fb_userid"),
                        rs.getString("slack_userid"),
                        rs.getString("telegram_userid"),
                        rs.getString("skype_userid"))
        );
        try {
            return users.get(0);
        } catch (Exception e) {
            log.info("User with FB messenger ID: " + fbUserid + " not found.");
            return null;
        }
    }

    public User findUserByTelegramID(String telegramUserid) {
        final String findUserByTelegramIDQuery = "SELECT * FROM " + tableName + " WHERE telegram_userid = '" + telegramUserid + "'";
        List<User> users = jdbcTemplate.query(
                findUserByTelegramIDQuery,
                (rs, rowNum) -> new User(
                        rs.getString("username"),
                        rs.getString("secret_key"),
                        rs.getString("fb_userid"),
                        rs.getString("slack_userid"),
                        rs.getString("telegram_userid"),
                        rs.getString("skype_userid"))
        );
        try {
            return users.get(0);
        } catch (Exception e) {
            log.info("User with telegram ID: " + telegramUserid + " not found.");
            return null;
        }
    }

    public User findUserBySlackID(String slackUserid) {
        final String findUserBySlackIDQuery = "SELECT * FROM " + tableName + " WHERE slack_userid = '" + slackUserid + "'";
        List<User> l = jdbcTemplate.query(
                findUserBySlackIDQuery,
                (rs, rowNum) -> new User(
                        rs.getString("Username"),
                        rs.getString("secret_key"),
                        rs.getString("fb_userid"),
                        rs.getString("slack_userid"),
                        rs.getString("telegram_userid"),
                        rs.getString("skype_userid"))
        );
        try {
            return l.get(0);
        } catch (Exception e) {
            log.info("User with slack ID: " + slackUserid + " not found.");
            return null;
        }
    }

    public void addUser(String username, String secretKey, String fbUserid, String slackUserid, String telegramUserid, String skypeUserid) {
        jdbcTemplate.update("INSERT INTO ? VALUES (?,?,?,?,?,?)",
                tableName, username, secretKey, fbUserid, slackUserid, telegramUserid, skypeUserid);
    }

    public void addUserByFBID(String username, String secretKey, String fbUserid) {
        jdbcTemplate.update("INSERT INTO ? (fb_userid, Username, secret_key) VALUES (?,?,?)",
                tableName, fbUserid, username, secretKey);
    }

    public void addUserByTelegramID(String username, String secretKey, String telegramUserid) {
        jdbcTemplate.update("INSERT INTO ? (telegram_userid, Username, secret_key) VALUES (?,?,?)",
                tableName, telegramUserid, username, secretKey);
    }

    public void addUserBySlackID(String username, String secretKey, String slackUserid) {
        jdbcTemplate.update("INSERT INTO ? (slack_userid, Username, secret_key) VALUES (?,?,?)",
                tableName, slackUserid, username, secretKey);
    }

    public void updateUserFBID(String username, String secretKey, String fbUserid) {
        jdbcTemplate.update("UPDATE ? SET fb_userid = ? WHERE Username = ? AND secret_key = ?",
                tableName, fbUserid, username, secretKey);
    }

    public void updateUserTelegramID(String username, String secretKey, String telegramUserid) {
        jdbcTemplate.update("UPDATE ? SET telegram_userid = ? WHERE Username = ? AND secret_key = ?",
                tableName, telegramUserid, username, secretKey);
    }

    public void updateUserSlackID(String username, String secretKey, String slackUserid) {
        jdbcTemplate.update("UPDATE ? SET slack_userid = ? WHERE Username = ? AND secret_key = ?",
                tableName, slackUserid, username, secretKey);
    }

    public void removeUser(String username) {
        jdbcTemplate.update("DELETE FROM " + tableName + " WHERE Username = '" + username + "'");
    }

    public boolean FBIDExist(String senderId) {
        User user = findUserByFBID(senderId);
        return user != null;
    }

    public boolean telegramIDExist(String senderId) {
        User user = findUserByTelegramID(senderId);
        return user != null;
    }

    public boolean slackIDExist(String senderId) {
        User user = findUserBySlackID(senderId);
        return user != null;
    }
}
