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
                Boolean authenticated = false;

                if(event.getMessageContent().toLowerCase().contains("username".toLowerCase())) {
                     authMap.put("Username", event.getMessageContent().replaceAll("(?i)username: ", ""));
                }
                if(event.getMessageContent().toLowerCase().contains("password".toLowerCase())) {
                    authMap.put("Password", event.getMessageContent().replaceAll("(?)password: ", ""));
                }
                if(authMap.get("Username").equals(settings.getUsername()) && authMap.get("Password").equals(settings.getPassword())) {
                     authenticated = true;
                }
                if(!authenticated && authMap.containsKey("Username") && authMap.containsKey("Password")){
                    MifosResponse response = new MifosResponse();
                    response.setContent("Please enter the correct username or password with correct tag");
                    // TODO: The reason why it will iterate over and over again is that it will always has the response, which contains the same msg content as the posted msg
//                    callback.onResponse(initialResponse);
                }

                if(callback != null && authenticated) {
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
                    } else {
                        MifosResponse errorResponse = new MifosResponse();
                        errorResponse.setContent("cannot find intent from it ");
//                        responseList.set(0, errorResponse);
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
