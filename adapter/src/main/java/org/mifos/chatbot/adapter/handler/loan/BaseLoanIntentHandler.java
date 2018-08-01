package org.mifos.chatbot.adapter.handler.loan;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.client.ApiClient;
import org.mifos.chatbot.core.IntentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
abstract class BaseLoanIntentHandler implements IntentHandler {

    @Autowired
    protected ApiClient apiClient;
}
