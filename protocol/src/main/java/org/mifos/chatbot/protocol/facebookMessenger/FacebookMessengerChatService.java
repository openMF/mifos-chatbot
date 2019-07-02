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
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.mifos.chatbot.client.ApiClient;
import org.mifos.chatbot.core.AdapterService;
import org.mifos.chatbot.core.model.MifosResponse;
import org.mifos.chatbot.core.model.MifosSettings;
import org.mifos.chatbot.database.dao.UserRepository;
import org.mifos.chatbot.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
    DataSource dataSource;

    @Autowired
    UserRepository userRepository;

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

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
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

    private boolean authUser(String username, String password) {
        HttpClient client = HttpClientBuilder.create().build();
        String authUrl = settings.getApiUrl() + "/authentication?username=" + username + "&password=" + password + "&tenantIdentifier=mifos";
        HttpPost post = new HttpPost(authUrl);
        try {
            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode() == 200) {
                return true;
            } else if(response.getStatusLine().getStatusCode() == 401) {
                log.info("Invalid login attempt with creds: {} and password: {}", username, password);
            }
        } catch (IOException e) {
            log.error(e.toString());
        }
        return false;
    }

    private static String base64Encode(String input) {
        final byte[] authBytes = input.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(authBytes);
    }

    private static String base64Decode(String input) {
        return String.valueOf(Base64.getDecoder().decode(input));
    }

    /**
     * This function is divided in 3 parts. First checks that message is to logout user, second checks that message is
     * to login user and third is to resolve message and send response to it.
     * @param event Facebook Messenger text message event
     */
    private void handleTextMessageEvent(TextMessageEvent event) {
        final String messageText = event.text();
        final String senderId = event.senderId();
        log.info("Facebook messenger: Message received of length " + messageText.length() + " from user: " + senderId);
        if (messageText.toLowerCase().contains("logout")) {
            handleLogout(senderId, messageText);
            return;
        }
        if (messageText.toLowerCase().contains("login:")) {
            handleLogin(senderId, messageText);
            return;
        }
        User user = userRepository.findUserByFBID(senderId);
        if(user != null) {
            handleMessageAndGenerateResponse(user, senderId, messageText);
        } else {
            sendTextMessage(senderId, "Please login first.");
        }
    }

    private void handleMessageAndGenerateResponse(User user, String senderId, String messageText) {
        String username = user.getUsername();
        String password = base64Decode(user.getSecret_Pass());
        if(authUser(username, password)) {
            ApiClient apiClient = new ApiClient(base64Encode(username + ":" + password));
            org.mifos.chatbot.client.Configuration.setDefaultApiClient(apiClient);
            List<MifosResponse> responseList = adapterService.handle(messageText.toLowerCase());
            if (!responseList.isEmpty()) {
                for (MifosResponse response : responseList) {
                    sendTextMessage(senderId, response.getContent());
                }
            } else {
                sendTextMessage(senderId, "Sorry i didn't get that.");
            }
        } else {
            sendTextMessage(senderId, "Your previous credentials are not correct. Please login again.");
            userRepository.removeUser(username);
        }
    }

    private void handleLogin(String senderId, String messageText) {
        StringBuilder username = new StringBuilder();
        StringBuilder password = new StringBuilder();
        String creds = messageText.replaceAll("login:", "");
        //extracting username and password from message
        for (int i = 0; i < creds.length(); i++) {
            if (creds.charAt(i) == ':') {
                for (i++; i < creds.length(); i++) {
                    password.append(creds.charAt(i));
                }
                break;
            }
            username.append(creds.charAt(i));
        }
        if(userRepository.FBIDExist(senderId)) {
            sendTextMessage(senderId, "You are already logged in, please logout first.");
        } else {
            if (authUser(username.toString(), password.toString())) {
                User user = userRepository.findUserByUsername(username.toString());
                if (user == null) {
                    userRepository.addUserByFBID(
                            username.toString(),
                            base64Encode(password.toString()),
                            senderId
                    );
                } else {
                    userRepository.updateUserFBID(
                            username.toString(),
                            base64Encode(password.toString()),
                            senderId
                    );
                }
                sendTextMessage(senderId, "Login successful.");
            } else {
                sendTextMessage(senderId, "Please enter valid credentials.");
            }
        }
    }

    private void handleLogout(String senderId, String messageText) {
        User user = userRepository.findUserByFBID(senderId);
        if(user != null) {
            userRepository.removeUser(user.getUsername());
            sendTextMessage(senderId, "Logged out successfully.");
        } else {
            sendTextMessage(senderId, "You are already logged out.");
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
        final String senderId = event.senderId();
        final String recipientId = event.recipientId();
        final String messageId = event.messageId();
        log.info("Received echo for message '{}' that has been sent to recipient '{}' by sender '{}' at '{}'", messageId, recipientId, senderId, event.timestamp());
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
        log.info("Telegram: Sending message of length " + text.length() + " to user: " + recipientId);
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