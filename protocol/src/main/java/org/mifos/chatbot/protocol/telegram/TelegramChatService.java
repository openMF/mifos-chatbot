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
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

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

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("Message received");
            final String messageText = update.getMessage().getText();
            final long senderId = update.getMessage().getChatId();
            if (messageText.toLowerCase().contains("logout")) {
                User user = userRepository.findUserByTelegramID(Long.toString(senderId));
                if(user != null) {
                    userRepository.removeUser(user.getUsername());
                    sendTextMessage(senderId, "Logged out successfully.");
                } else {
                    sendTextMessage(senderId, "You are already logged out.");
                }
                return;
            }
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
                if(userRepository.TelegramIDExist(Long.toString(senderId))) {
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
                        sendTextMessage(senderId, "Login successfully.");
                    } else {
                        sendTextMessage(senderId, "Please enter valid credentials.");
                        return;
                    }
                }
                return;
            }
            User user = userRepository.findUserByTelegramID(Long.toString(senderId));
            if(user != null) {
                String username = user.getUsername();
                String password = user.getSecret_Pass();
                if(authUser(username, password)) {
                    ApiClient apiClient = new ApiClient(base64Encode(username + ":" + password));
                    org.mifos.chatbot.client.Configuration.setDefaultApiClient(apiClient);
                    System.out.println("ooo");
                    List<MifosResponse> responseList = adapterService.handle(messageText.toLowerCase());
                    System.out.println("111");
                    System.out.println(responseList.size());
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
            } else {
                sendTextMessage(senderId, "Please login first.");
            }
        }
    }

    private void sendTextMessage(long recipientId, String text) {
        SendMessage message = new SendMessage()
                .setChatId(recipientId)
                .setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private boolean authUser(String username, String password) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(settings.getApiUrl() + "/authentication?username=" + username + "&password=" + password + "&tenantIdentifier=mifos");
        try {
            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return false;
    }

    private static String base64Encode(String input) {
        final byte[] authBytes = input.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(authBytes);
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
        System.out.println("Running telegram chat service");
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
