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
package org.mifos.chatbot.protocol.slack;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.mifos.chatbot.client.ApiClient;
import org.mifos.chatbot.core.AdapterService;
import org.mifos.chatbot.core.ChatService;
import org.mifos.chatbot.core.model.Message;
import org.mifos.chatbot.core.model.MifosResponse;
import org.mifos.chatbot.core.model.MifosSettings;
import org.mifos.chatbot.database.dao.UserRepository;
import org.mifos.chatbot.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class SlackChatService implements ChatService {

    @Autowired
    private SlackSession session;

    private ChatCallBack callback;

    @Autowired
    private MifosSettings settings;

    @Autowired
    private AdapterService adapterService;

    @Autowired
    DataSource dataSource;

    @Autowired
    UserRepository userRepository;

    public SlackChatService() {

    }

    public SlackChatService(SlackSession session) {
        this.session = session;
    }

    @Override
    public void connect(ChatCallBack callback) {
        try {
            this.callback = callback;
            session.connect();
            session.addMessagePostedListener((event, session) -> {
                if(event.getSender().isBot()) {
                    return;
                }
                final String senderId = event.getSender().getUserMail();
                final String messageText = event.getMessageContent();
                MifosResponse mifosResponse = new MifosResponse();
                log.info("Slack: Message received of length " + messageText.length() + " from user: " + senderId);
                if (messageText.toLowerCase().contains("logout")) {
                    User user = userRepository.findUserBySlackID(senderId);
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
                    if(userRepository.slackIDExist(senderId)) {
                        sendTextMessage(senderId, "You are already logged in, please logout first.");
                    } else {
                        if (authUser(username.toString(), password.toString())) {
                            User user = userRepository.findUserByUsername(username.toString());
                            if (user == null) {
                                userRepository.addUserBySlackID(
                                        username.toString(),
                                        password.toString(),
                                        senderId
                                );
                            } else {
                                userRepository.updateUserSlackID(
                                        username.toString(),
                                        password.toString(),
                                        senderId
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
                User user = userRepository.findUserBySlackID(senderId);
                if(user != null) {
                    String username = user.getUsername();
                    String password = user.getSecret_Pass();
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
                } else {
                    sendTextMessage(senderId, "Please login first.");
                }
            });
        } catch (Exception e) {
            log.error(e.toString(), e);
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

    private void sendTextMessage(String recipientId, String text) {
        MifosResponse mifosResponse = new MifosResponse();
        mifosResponse.setContent(text);
        callback.onResponse(mifosResponse, recipientId);
    }

    @Override
    public void disconnect() {
        try {
            session.disconnect();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }

    @Override
    public void send(Message msg) {
        SlackUser user = session.findUserByEmail(msg.getTo());
        session.sendMessageToUser(user, msg.getText(), null);
    }
}
