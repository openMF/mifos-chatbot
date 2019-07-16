package org.mifos.chatbot.database.dao;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        final String findAllQuery = "SELECT * FROM `" + tableName + "`";
        return jdbcTemplate.query(
                findAllQuery,
                (rs, rowNum) -> new User(rs.getString("username"),
                        rs.getString("secret_key"), rs.getString("fb_userid"), rs.getString("slack_userid"), rs.getString("telegram_userid"), rs.getString("skype_userid"))
        );
    }

    public User findUserByUsername(String username) {
        final String findUserByUsernameQuery = "SELECT * FROM `" + tableName + "` WHERE username = '" + username + "'";
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
        final String findUserByFBIDQuery = "SELECT * FROM `" + tableName + "` WHERE fb_userid = '" + fbUserid + "'";
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
        final String findUserByTelegramIDQuery = "SELECT * FROM `" + tableName + "` WHERE telegram_userid = '" + telegramUserid + "'";
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
        final String findUserBySlackIDQuery = "SELECT * FROM `" + tableName + "` WHERE slack_userid = '" + slackUserid + "'";
        List<User> l = jdbcTemplate.query(
                findUserBySlackIDQuery,
                (rs, rowNum) -> new User(
                        rs.getString("username"),
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
        jdbcTemplate.update("INSERT INTO `"+tableName+"` VALUES (?,?,?,?,?,?)",
                username, secretKey, fbUserid, slackUserid, telegramUserid, skypeUserid);
    }

    public void addUserByFBID(String username, String secretKey, String fbUserid) {
        jdbcTemplate.update("INSERT INTO `"+tableName+"` (fb_userid, username, secret_key) VALUES (?,?,?)",
                fbUserid, username, secretKey);
    }

    public void addUserByTelegramID(String username, String secretKey, String telegramUserid) {
        jdbcTemplate.update("INSERT INTO `"+tableName+"` (telegram_userid, username, secret_key) VALUES (?,?,?)",
                telegramUserid, username, secretKey);
    }

    public void addUserBySlackID(String username, String secretKey, String slackUserid) {
        try {
            jdbcTemplate.update("INSERT INTO `"+tableName+"` (slack_userid, username, secret_key) VALUES (?,?,?)",
                    slackUserid, username, secretKey);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateUserFBID(String username, String secretKey, String fbUserid) {
        jdbcTemplate.update("UPDATE `"+tableName+"` SET fb_userid = ? WHERE username = ? AND secret_key = ?",
                fbUserid, username, secretKey);
    }

    public void updateUserTelegramID(String username, String secretKey, String telegramUserid) {
        jdbcTemplate.update("UPDATE `"+tableName+"` SET telegram_userid = ? WHERE username = ? AND secret_key = ?",
                telegramUserid, username, secretKey);
    }

    public void updateUserSlackID(String username, String secretKey, String slackUserid) {
        jdbcTemplate.update("UPDATE `"+tableName+"` SET slack_userid = ? WHERE username = ? AND secret_key = ?",
                slackUserid, username, secretKey);
    }

    public void removeUser(String username) {
        jdbcTemplate.update("DELETE FROM `" + tableName + "` WHERE username = '" + username + "'");
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
