package org.mifos.chatbot.server;

import org.mifos.chatbot.core.ChatService;
import org.mifos.chatbot.core.model.Message;

/**
 * This service handles the request, which is the user input and return the response, which is the result called from Mifos API
 */

public class ChatWithUserService implements ChatService {

    @Override
    public void connect(ChatCallBack chatCallBack) {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void send(Message msg) {

    }
}
