package org.mifos.chatbot.protocol.telegram;

import lombok.extern.slf4j.Slf4j;
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
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class TelegramChatService extends TelegramLongPollingBot {

    @Autowired
    private MifosSettings settings;

    @Autowired
    private AdapterService adapterService;

    @Autowired
    DataSource dataSource;

    @Autowired
    UserRepository userRepository;

    @Value("${telegram.botUsername}")
    private String botUsername;

    @Value("${telegram.botToken}")
    private String botToken;

    /**
     * This function is divided in 3 parts. First checks that message is to logout user, second checks that message is
     * to login user and third is to resolve message and send response to it.
     * @param update Telegram message update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String messageText = update.getMessage().getText();
            final long senderId = update.getMessage().getChatId();
            log.info("Telegram: Message received of length " + messageText.length() + " from user: " + senderId);
            if (messageText.toLowerCase().contains("logout")) {
                handleLogout(senderId, messageText);
                return;
            }
            if (messageText.toLowerCase().contains("login:")) {
                handleLogin(senderId, messageText);
                return;
            }
            if(adapterService.isSmallTalkRequest(messageText.toLowerCase())) {
                List<MifosResponse> responseList = adapterService.handle(messageText.toLowerCase());
                if (!responseList.isEmpty()) {
                    for (MifosResponse response : responseList) {
                        Collection<String> chunk = createChunk(response.getContent(), 1000);
                        for (String str : chunk) {
                            sendTextMessage(senderId, str);
                        }
                    }
                } else {
                    sendTextMessage(senderId, "Can you please try saying that in different way.");
                }
                return;
            }
            User user = userRepository.findUserByTelegramID(Long.toString(senderId));
            if(user != null) {
                handleMessageAndGenerateResponse(user, senderId, messageText);
            } else {
                sendTextMessage(senderId, "Please login first.");
            }
        }
    }

    private void handleMessageAndGenerateResponse(User user, long senderId, String messageText) {
        String username = user.getUsername();
        String password = user.getSecret_Pass();
        if(authUser(username, password)) {
            ApiClient apiClient = new ApiClient(base64Encode(username + ":" + password));
            org.mifos.chatbot.client.Configuration.setDefaultApiClient(apiClient);
            List<MifosResponse> responseList = adapterService.handle(messageText.toLowerCase());
            if (!responseList.isEmpty()) {
                for (MifosResponse response : responseList) {
                    Collection<String> chunk = createChunk(response.getContent(), 1000);
                    for (String str : chunk) {
                        sendTextMessage(senderId, str);
                    }
                }
            } else {
                sendTextMessage(senderId, "Sorry i didn't get that.");
            }
        } else {
            sendTextMessage(senderId, "Your previous credentials are not correct. Please login again.");
            userRepository.removeUser(username);
        }
    }

    private void handleLogin(long senderId, String messageText) {
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
        if(userRepository.telegramIDExist(Long.toString(senderId))) {
            sendTextMessage(senderId, "You are already logged in, please logout first.");
        } else {
            if (authUser(username.toString(), password.toString())) {
                User user = userRepository.findUserByUsername(username.toString());
                if (user == null) {
                    userRepository.addUserByTelegramID(
                            username.toString(),
                            password.toString(),
                            Long.toString(senderId)
                    );
                } else {
                    userRepository.updateUserTelegramID(
                            username.toString(),
                            password.toString(),
                            Long.toString(senderId)
                    );
                }
                sendTextMessage(senderId, "Login successful.");
            } else {
                sendTextMessage(senderId, "Please enter valid credentials.");
            }
        }
    }

    private void handleLogout(long senderId, String messageText) {
        User user = userRepository.findUserByTelegramID(Long.toString(senderId));
        if(user != null) {
            userRepository.removeUser(user.getUsername());
            sendTextMessage(senderId, "Logged out successfully.");
        } else {
            sendTextMessage(senderId, "You are already logged out.");
        }
    }

    private void sendTextMessage(long recipientId, String text) {
        log.info("Telegram: Sending message of length " + text.length() + " to user: " + recipientId);
        SendMessage message = new SendMessage()
                .setChatId(recipientId)
                .setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(Arrays.toString(e.getStackTrace()));
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

    private static Collection<String> createChunk(String input, int size) {
        ArrayList<String> split = new ArrayList<>();
        for (int i = 0; i <= input.length() / size; i++) {
            split.add(input.substring(i * size, Math.min(input.length(), (i + 1) * size)));
        }
        return split;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @PostConstruct
    public void registerBot() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(this);
            log.info("Telegram: Telegram bot registered");
        } catch (TelegramApiException e) {
            log.error("Telegram: Unable to register telegram bot");
            e.printStackTrace();
        }
    }
}
