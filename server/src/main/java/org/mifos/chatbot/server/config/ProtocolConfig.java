package org.mifos.chatbot.server.config;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.model.MifosSettings;
import org.mifos.chatbot.protocol.slack.SlackChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProtocolConfig {
    @Autowired
    private MifosSettings settings;

    @Bean
    public SlackSession slackSession() {
        return SlackSessionFactory.createWebSocketSlackSession(settings.getSlackApiToken());
    }

    @Bean
    public SlackChatService slackChatService(SlackSession session) {
        SlackChatService slackChatService = new SlackChatService(session);
        slackChatService.connect(msg -> log.info("We've got a response: {}", msg.getText()));
        return slackChatService;
    }
}
