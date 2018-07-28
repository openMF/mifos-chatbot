package org.mifos.chatbot.adapter.handler;

import io.swagger.annotations.OAuth2Definition;
import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.LoansApi;
import org.mifos.chatbot.client.model.GetHolidaysResponse;
import org.mifos.chatbot.client.model.GetLoansLoanIdResponse;
import org.mifos.chatbot.client.model.GetLoansResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class NextDueDateHandler extends BaseIntentHandler {
    private static final String INTENT_KEYWORD = "DueDate";

    @Autowired
    private LoansApi loansApi;

    @Override
    public Boolean canHandle(Intent intent) {
        log.info(INTENT_KEYWORD.toLowerCase());
        return intent.getKeyword().toLowerCase().contains(INTENT_KEYWORD.toLowerCase());
    }

    @Override
    public MifosResponse handle(Intent intent) {
        MifosResponse response = new MifosResponse();
        try {
            GetLoansLoanIdResponse result = loansApi.retrieveLoan(1L, false);

            // TODO: Correct the response, current response is just for unit test
            response.setContent(result.getSummary().getPrincipalOutstanding().toString());

        } catch (ApiException e) {
            log.error(e.toString(), e);
            response.setContent(e.getMessage());
        }

        return response;
    }
}
