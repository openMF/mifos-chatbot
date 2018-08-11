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

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mifos.chatbot.core.model.Message;
import org.mifos.chatbot.protocol.slack.SlackChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class SlackServiceTest {

    @Autowired
    private SlackChatService slackChatService;

    @Before
    public void setUp() {
        slackChatService.connect(msg -> log.info("We've got a response: {}", msg.getText()));
    }

    @After
    public void tearDown() {
        slackChatService.disconnect();
    }

    @Test
    public void sendMessage() {
        Message m = new Message();
        m.setTo("zhaodingfanhaha@gmail.com");
        m.setText("Hello World from Dingfan!");
        slackChatService.send(m);
    }

    @Test
    public void receiveMessage() {
        // TODO: implement this
    }
}
