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
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.AdapterService;
import org.mifos.chatbot.core.ChatService;
import org.mifos.chatbot.core.model.Message;
import org.mifos.chatbot.core.model.MifosResponse;
import org.mifos.chatbot.core.model.MifosSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Map<String, String> authMap = new HashMap<>();
            callback.onCheckingUsernameAndPassword();

            session.addMessagePostedListener((event, session) -> {
                final String noAuth = "Please enter the correct username or password with correct tag";
                final String noIntent = "cannot find intent from it ";
                final String messageText = event.getMessageContent();
                //todo work when implementing auth solution
//                Boolean authenticated = false;
//
//                if(event.getMessageContent().toLowerCase().contains("username".toLowerCase())) {
//                     authMap.put("Username", event.getMessageContent().replaceAll("(?i)username: ", ""));
//                }
//                if(event.getMessageContent().toLowerCase().contains("password".toLowerCase())) {
//                    authMap.put("Password", event.getMessageContent().replaceAll("(?)password: ", ""));
//                }
//                if(authMap.get("Username").equals(settings.getUsername()) && authMap.get("Password").equals(settings.getPassword())) {
//                     authenticated = true;
//                }
//                if(!authenticated && authMap.containsKey("Username") && authMap.containsKey("Password") && !event.getMessageContent().equals(noAuth)){
//                    MifosResponse response = new MifosResponse();
//                    response.setContent(noAuth);
//                    // TODO: The reason why it will iterate over and over again is that it will always has the response, which contains the same msg content as the posted msg
//                    callback.onResponse(response);
//                }

//                if(callback != null && authenticated) {
                if(callback != null) {
                    Message m = new Message();
                    m.setFrom(event.getSender().getUserMail());
                    m.setText(event.getMessageContent());

                    callback.onMessage(m);

                    log.info("Slack : " + event.getMessageContent());
                    List<MifosResponse> responseList = adapterService.handle(event.getMessageContent());

                    if(!responseList.isEmpty()) {
                        for (MifosResponse response : responseList) {
                            callback.onResponse(response);
                        }
                    } else if(!event.getMessageContent().equals(noIntent)) {
                        MifosResponse errorResponse = new MifosResponse();
                        errorResponse.setContent(noIntent);
                        callback.onResponse(errorResponse);
                    }
                }
            });
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
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
