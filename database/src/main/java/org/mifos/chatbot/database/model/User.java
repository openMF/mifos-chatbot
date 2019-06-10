package org.mifos.chatbot.database.model;

import javax.persistence.*;

@Entity
public class User {

    @Column(nullable = false)
    private String Username;

    @Column(nullable = false)
    private String secret_Pass;

    private String FB_userID;

    private String slack_userID;

    private String telegram_userID;

    private String skype_userID;

    public User(String username, String secret_Pass, String FB_userID, String slack_userID, String telegram_userID, String skype_userID) {
        Username = username;
        this.secret_Pass = secret_Pass;
        this.FB_userID = FB_userID;
        this.slack_userID = slack_userID;
        this.telegram_userID = telegram_userID;
        this.skype_userID = skype_userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getSecret_Pass() {
        return secret_Pass;
    }

    public void setSecret_Pass(String secret_Pass) {
        this.secret_Pass = secret_Pass;
    }

    public String getFB_userID() {
        return FB_userID;
    }

    public void setFB_userID(String FB_userID) {
        this.FB_userID = FB_userID;
    }

    public String getSlack_userID() {
        return slack_userID;
    }

    public void setSlack_userID(String slack_userID) {
        this.slack_userID = slack_userID;
    }

    public String getTelegram_userID() {
        return telegram_userID;
    }

    public void setTelegram_userID(String telegram_userID) {
        this.telegram_userID = telegram_userID;
    }

    public String getSkype_userID() {
        return skype_userID;
    }

    public void setSkype_userID(String skype_userID) {
        this.skype_userID = skype_userID;
    }
}
