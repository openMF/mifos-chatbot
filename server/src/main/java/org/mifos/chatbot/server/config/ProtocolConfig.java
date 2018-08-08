package org.mifos.chatbot.server.config;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.ChatService;
import org.mifos.chatbot.core.model.Message;
import org.mifos.chatbot.core.model.MifosResponse;
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

        slackChatService.connect(new ChatService.ChatCallBack() {
            @Override
            public void onMessage(Message msg) {
                log.info("We've got a response: {}", msg.getText());
            }

            @Override
            public void onResponse(MifosResponse response) {
                Message msg = new Message();
                msg.setText(response.getContent());
                msg.setTo("zhaodingfanhaha@gmail.com");
                slackChatService.send(msg);
            }

            @Override
            public void onCheckingUsernameAndPassword() {
                Message msg = new Message();
                msg.setText("Please key in your username and password with tag of `username: ` and `password: `(be careful about the space) " +
                        "\nRemember to put in two messages~");
                msg.setTo("zhaodingfanhaha@gmail.com");
                slackChatService.send(msg);
            }
        });

        return slackChatService;
    }
}
