package org.mifos.chatbot.adapter.handler.loan;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.adapter.handler.HandlerUtils;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.ClientApi;
import org.mifos.chatbot.client.model.GetClientsClientIdResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ClientActivationDateHandler extends BaseLoanIntentHandler {
    private static final String[] INTENT_KEYWORDS = {"client", "activation", "date"};

    @Autowired
    private ClientApi clientApi;

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
            GetClientsClientIdResponse result = clientApi.retrieveOne(intent.getParameterAsLong("ID"), false);
            List<Long> date = result.getActivationDate();
            response.setContent(HandlerUtils.convertListToDate(date).toString());
        } catch (ApiException e) {
            log.info("Error", e);
            response.setContent(e.getMessage());
        }

        return response;
    }
}
