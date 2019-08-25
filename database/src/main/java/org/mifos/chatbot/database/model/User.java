package org.mifos.chatbot.database.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

}
