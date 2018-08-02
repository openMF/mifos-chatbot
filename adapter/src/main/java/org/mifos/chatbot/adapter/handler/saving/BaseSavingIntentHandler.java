package org.mifos.chatbot.adapter.handler.saving;

import org.mifos.chatbot.client.ApiClient;
import org.mifos.chatbot.core.IntentHandler;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseSavingIntentHandler implements IntentHandler {

    @Autowired
    ApiClient apiClient;
}
