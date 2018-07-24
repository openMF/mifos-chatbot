package org.mifos.chatbot.protocol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This protocol module(Slack part, there maybe some other protocols later) is based on JBot Framework
 *
 * The code refers to the example of JBot application provided by JBot
 * https://github.com/rampatra/jbot/blob/master/jbot-example/src/main/java/example/jbot/JBotApplication.java
 */

@SpringBootApplication
public class SlackIntegrationApplication {
    /**
     * Entry point of the application. Run this method to start the sample bots,
     * but don't forget to add the correct tokens in application.properties file.
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SlackIntegrationApplication.class, args);
    }
}
