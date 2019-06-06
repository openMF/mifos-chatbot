package org.mifos.chatbot.protocol.facebookMessenger;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.exception.MessengerVerificationException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.send.recipient.IdRecipient;
import com.github.messenger4j.webhook.Event;
import com.github.messenger4j.webhook.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.mifos.chatbot.core.AdapterService;
import org.mifos.chatbot.core.model.MifosResponse;
import org.mifos.chatbot.core.model.MifosSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.github.messenger4j.Messenger.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * This is the main class for inbound and outbound communication with the Facebook Messenger Platform. The callback
 * handler is responsible for the webhook verification and processing of the inbound messages and events. It showcases
 * the features of the Messenger Platform.
 *
 * @author Max Grabenhorst
 */
@Slf4j
@RestController
@RequestMapping("/callback")
public class FacebookMessengerChatService {

    @Autowired
    private MifosSettings settings;

    private final Messenger messenger;

    @Autowired
    private AdapterService adapterService;

    @Autowired
    public FacebookMessengerChatService(final Messenger messenger) {
        this.messenger = messenger;
    }

    @Value("${developer.metadata}")
    private String metadata;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<String> verifyWebhook(@RequestParam(MODE_REQUEST_PARAM_NAME) final String mode,
                                                @RequestParam(VERIFY_TOKEN_REQUEST_PARAM_NAME) final String verifyToken, @RequestParam(CHALLENGE_REQUEST_PARAM_NAME) final String challenge) {
        try {
            this.messenger.verifyWebhook(mode, verifyToken);
            return ResponseEntity.ok(challenge);
        } catch (MessengerVerificationException e) {
            log.warn("Webhook verification failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> handleCallback(@RequestBody final String payload, @RequestHeader(SIGNATURE_HEADER_NAME) final String signature) {
        try {
            this.messenger.onReceiveEvents(payload, of(signature), event -> {
                if (event.isTextMessageEvent()) {
                    handleTextMessageEvent(event.asTextMessageEvent());
                } else if (event.isAttachmentMessageEvent()) {
                    handleAttachmentMessageEvent(event.asAttachmentMessageEvent());
                } else if (event.isQuickReplyMessageEvent()) {
                    handleQuickReplyMessageEvent(event.asQuickReplyMessageEvent());
                } else if (event.isPostbackEvent()) {
                    handlePostbackEvent(event.asPostbackEvent());
                } else if (event.isAccountLinkingEvent()) {
                    handleAccountLinkingEvent(event.asAccountLinkingEvent());
                } else if (event.isOptInEvent()) {
                    handleOptInEvent(event.asOptInEvent());
                } else if (event.isMessageEchoEvent()) {
                    handleMessageEchoEvent(event.asMessageEchoEvent());
                } else if (event.isMessageDeliveredEvent()) {
                    handleMessageDeliveredEvent(event.asMessageDeliveredEvent());
                } else if (event.isMessageReadEvent()) {
                    handleMessageReadEvent(event.asMessageReadEvent());
                } else {
                    handleFallbackEvent(event);
                }
            });
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (MessengerVerificationException e) {
            log.error(e.getStackTrace().toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NotImplementedException e) {
            log.error(e.getStackTrace().toString());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    private void handleTextMessageEvent(TextMessageEvent event) {
        final String messageText = event.text();
        final String senderId = event.senderId();

        //todo manage login
        if (messageText.toLowerCase().contains("login:")) {
            StringBuilder username = new StringBuilder();
            StringBuilder password = new StringBuilder();
            String creds = messageText.replaceAll("login:", "");
            for (int i = 0; i < creds.length(); i++) {
                if (creds.charAt(i) == ':') {
                    for (i++; i < creds.length(); i++) {
                        password.append(creds.charAt(i));
                    }
                    break;
                }
                username.append(creds.charAt(i));
            }
            System.out.println(username.toString()+"#"+password.toString());
            return;
        }
        List<MifosResponse> responseList = adapterService.handle(messageText.toLowerCase());
        if (!responseList.isEmpty()) {
            for (MifosResponse response : responseList) {
                sendTextMessage(senderId, response.getContent());
            }
        }
    }

    private void handleAttachmentMessageEvent(AttachmentMessageEvent event) {
        throw new NotImplementedException("User " + event.senderId() + " tries to send attachment. Application doesn't support attachment.");
    }

    private void handleQuickReplyMessageEvent(QuickReplyMessageEvent event) {
        throw new NotImplementedException("User " + event.senderId() + " taped on quick reply. Payload: " + event.payload() + "Application don't need quick reply.");
    }

    private void handlePostbackEvent(PostbackEvent event) {
        throw new NotImplementedException("User " + event.senderId() + " taped on Postback event at " + event.timestamp().toString() + ". Payload: " + event.payload());
    }

    private void handleAccountLinkingEvent(AccountLinkingEvent event) {
        throw new NotImplementedException("User " + event.senderId() + " taped on Account linking event. Application doesn't support account linking.");
    }

    private void handleOptInEvent(OptInEvent event) {
        throw new NotImplementedException("User " + event.senderId() + " taped on Opt in event. Application doesn't support Opt-in event.");
    }

    private void handleMessageEchoEvent(MessageEchoEvent event) {
        throw new NotImplementedException("User " + event.senderId() + " taped on Echo event. Application doesn't support Message-echo event.");
    }

    private void handleMessageDeliveredEvent(MessageDeliveredEvent event) {
        final List<String> messageIds = event.messageIds().orElse(Collections.emptyList());
        messageIds.forEach(messageId -> log.info("Received delivery confirmation for message " + messageId));
        log.info("All messages before " + event.watermark().toString() + " were delivered to user " + event.senderId());
    }

    private void handleMessageReadEvent(MessageReadEvent event) {
        log.info("All messages before " + event.watermark().toString() + " were read by user " + event.senderId());
    }

    private void handleFallbackEvent(Event event) {
        log.info("Received unsupported message from user " + event.senderId());
    }

    private void sendTextMessage(String recipientId, String text) {
        try {
            final IdRecipient recipient = IdRecipient.create(recipientId);
            final NotificationType notificationType = NotificationType.REGULAR;

            final TextMessage textMessage = TextMessage.create(text, empty(), of(metadata));
            final MessagePayload messagePayload = MessagePayload.create(recipient, MessagingType.RESPONSE, textMessage,
                    of(notificationType), empty());
            this.messenger.send(messagePayload);
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Message could not be sent. An unexpected error occurred.", e);
        }
    }
}