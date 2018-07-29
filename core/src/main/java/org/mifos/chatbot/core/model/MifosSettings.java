package org.mifos.chatbot.core.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mifos")
public class MifosSettings {
    private String apiUrl;

    private String slackApiToken;

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
}
