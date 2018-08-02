package org.mifos.chatbot.adapter.handler.loanProduct;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.client.ApiClient;
import org.mifos.chatbot.core.IntentHandler;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class BaseLoanProductIntentHandler implements IntentHandler {

    @Autowired
    protected ApiClient apiClient;
}
