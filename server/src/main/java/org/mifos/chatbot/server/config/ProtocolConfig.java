/**
 * Copyright 2018 Dingfan Zhao
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProtocolConfig {

    @Value("${user.slackEmail}")
    private String userEmail;

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
            public void onResponse(MifosResponse response, String recipientId) {
                Message msg = new Message();
                msg.setText(response.getContent());
                msg.setTo(recipientId);
                slackChatService.send(msg);
            }

        });

        return slackChatService;
    }
}
