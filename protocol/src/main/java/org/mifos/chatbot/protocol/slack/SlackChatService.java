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
                if(callback!=null) {
                    Message m = new Message();
                    m.setFrom(event.getSender().getUserMail());
                    m.setText(event.getMessageContent());

                    callback.onMessage(m);

                    log.info("Slack : " + event.getMessageContent());
                    List<MifosResponse> responseList = adapterService.handle(event.getMessageContent());

                    for(MifosResponse response : responseList) {
                        callback.onResponse(response);
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
