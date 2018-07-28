package org.mifos.chatbot.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@Profile("slack")
public class SlackWebhooks {
    @Value("${slackIncomingWebhookUrl}")
    private String slackIncomingWebhookUrl;

    /**
     * Make a POST call to the incoming webhook url.
     */
    @PostConstruct
    public void invokeSlackWebhook() {
        RestTemplate restTemplate = new RestTemplate();
        RichMessage richMessage = new RichMessage("Just to test Slack's incoming webhooks.");
        // set attachments
        Attachment[] attachments = new Attachment[1];
        attachments[0] = new Attachment();
        attachments[0].setText("Some data relevant to your users.");
        richMessage.setAttachments(attachments);

        // For debugging purpose only
        try {
            log.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
        } catch (JsonProcessingException e) {
            log.debug("Error parsing RichMessage: ", e);
        }

        // Always remember to send the encoded message to Slack
        try {
            restTemplate.postForEntity(slackIncomingWebhookUrl, richMessage.encodedMessage(), String.class);
        } catch (RestClientException e) {
            log.error("Error posting to Slack Incoming Webhook: ", e);
        }
    }
}
