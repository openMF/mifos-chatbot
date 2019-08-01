package org.mifos.chatbot.protocol.skype;

import org.mifos.chatbot.core.AdapterService;
import org.mifos.chatbot.core.ChatService;
import org.mifos.chatbot.core.model.Message;
import org.mifos.chatbot.core.model.MifosSettings;
import org.mifos.chatbot.database.dao.UserRepository;
import org.mifos.chatbot.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class SkypeChatService implements ChatService{

    private ChatService.ChatCallBack callback;

    @Autowired
    private MifosSettings settings;

    @Autowired
    private AdapterService adapterService;

    @Autowired
    DataSource dataSource;

    @Autowired
    UserRepository userRepository;

    @Override
    public void connect(ChatService.ChatCallBack callback) {
    }

    private void handleMessageAndGenerateResponse(User user, String senderId, String messageText) {
    }

    @Override
    public void disconnect() {
    }

    @Override
    public void send(Message msg) {
    }
}
