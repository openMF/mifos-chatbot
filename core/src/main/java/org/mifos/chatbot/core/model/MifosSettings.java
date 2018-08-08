package org.mifos.chatbot.core.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mifos")
public class MifosSettings {
    private String apiUrl;

    private String slackApiToken;

    private String username;

    private String password;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getSlackApiToken() {
        return slackApiToken;
    }

    public void setSlackApiToken(String slackApiToken) {
        this.slackApiToken = slackApiToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
