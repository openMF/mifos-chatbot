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
        m.setTo("Dingfan");
        m.setText("Hello World from Dingfan!");
        slackChatService.send(m);
    }

    @Test
    public void receiveMessage() {
        // TODO: implement this
    }
}
