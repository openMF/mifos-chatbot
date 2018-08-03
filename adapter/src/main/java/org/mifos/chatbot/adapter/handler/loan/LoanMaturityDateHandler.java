package org.mifos.chatbot.adapter.handler.loan;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.adapter.handler.HandlerUtils;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.LoansApi;
import org.mifos.chatbot.client.model.GetLoansLoanIdResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoanMaturityDateHandler extends BaseLoanIntentHandler {
    private static final String INTENT_KEYWORD = "maturityDate";

    @Autowired
    private LoansApi loansApi;

    @Override
    public Boolean canHandle(Intent intent) {
        return intent.getKeyword().toLowerCase().contains(INTENT_KEYWORD.toLowerCase());
    }

    @Override
    public MifosResponse handle(Intent intent) {
        MifosResponse response = new MifosResponse();
        try {
            GetLoansLoanIdResponse result = loansApi.retrieveLoan(2L, false);
            response.setContent(HandlerUtils.convertListToDate(result.getTimeline().getExpectedMaturityDate()).toString());
        } catch (ApiException e) {
            log.info("Error", e);
            response.setContent(e.getMessage());
        }

        return response;
    }
}
