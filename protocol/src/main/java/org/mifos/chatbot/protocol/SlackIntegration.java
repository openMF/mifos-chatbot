package org.mifos.chatbot.protocol;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used for integration with Slack bot, user input module
 *
 * I plan to use JBot Framework to help me care about the underlying details, like the connection with Websocket
 * https://dzone.com/articles/make-slack-bots-in-java-in-minutes
 */
@Slf4j
public class SlackIntegration {
//    public void startBot() {
//        SlackSession session = SlackSessionFactory.createWebSocketSlackSession("slack-bot-auth-token");
//        try {
//            session.connect();
//        } catch (IOException e) {
//            log.error("Something wrong with initialization step : ", e);
//        }
//        SlackChannel channel = session.findChannelByName("general"); //make sure bot is a member of the channel.
//        session.sendMessage(channel, "hi im a bot" );
//    }

}
