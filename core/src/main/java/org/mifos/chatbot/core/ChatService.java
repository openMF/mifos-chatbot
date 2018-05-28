package org.mifos.chatbot.core;

import org.mifos.chatbot.core.model.Message;

/**
 * User input
 */

public interface ChatService {
    void connect(ChatCallBack chatCallBack);
    void disconnect();
    void send(Message msg);

    interface ChatCallBack {
        void onMessage(Message msg);
    }
}
