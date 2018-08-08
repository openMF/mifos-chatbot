package org.mifos.chatbot.adapter.handler.saving;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.adapter.handler.HandlerUtils;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.SavingsAccountApi;
import org.mifos.chatbot.client.model.GetSavingsAccountsAccountIdResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SavingBalanceHandler extends BaseSavingIntentHandler {
    private static final String[] INTENT_KEYWORDS = {"saving", "Balance"};

    @Autowired
    private SavingsAccountApi savingsAccountApi;

    @Override
    public Boolean canHandle(Intent intent) {
        for(String intent_keyword : INTENT_KEYWORDS) {
            if (!intent.getKeyword().toLowerCase().contains(intent_keyword.toLowerCase()))
                return false;
        }

        return true;
    }

    @Override
    public MifosResponse handle(Intent intent) {
        MifosResponse response = new MifosResponse();
        try {
            GetSavingsAccountsAccountIdResponse result = savingsAccountApi.retrieveOne(intent.getParameterAsLong("ID"), false, "all");
            response.setContent(String.valueOf(result.getSummary().getAccountBalance()));
        } catch (ApiException e) {
            log.info("Error", e);
            response.setContent(e.getMessage());
        }

        return response;
    }
}
