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
package org.mifos.chatbot.protocol;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.AdapterService;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.mifos.chatbot.core.model.MifosSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
@ComponentScan("org.mifos.chatbot")
public class TestConfiguration {

    @Autowired
    private MifosSettings settings;

    @Bean
    public SlackSession slackSession() {
        return SlackSessionFactory.createWebSocketSlackSession(settings.getSlackApiToken());
    }

    @Bean
    public AdapterService adapterService() {
        return new AdapterService() {
            @Override
            public List<MifosResponse> handle(Intent intent) {
                return Collections.emptyList();
            }

            @Override
            public List<MifosResponse> handle(String input) {
                log.info("RECEIVED MESSAGE: {}", input);

                return Collections.emptyList();
            }
        };
    }
}
