package org.mifos.chatbot.adapter.handler.loan;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.LoansApi;
import org.mifos.chatbot.client.model.GetLoansLoanIdResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoanStatusHandler extends BaseLoanIntentHandler {
    private static final String[] INTENT_KEYWORDS = {"loan", "Status"};

    @Autowired
    private LoansApi loansApi;

    @Override
    public Boolean canHandle(Intent intent) {
        // TODO: improve if necessary
        for(String intent_keyword : INTENT_KEYWORDS) {
            if (!intent.getKeyword().toLowerCase().contains(intent_keyword.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public MifosResponse handle(Intent intent) {
        MifosResponse response = new MifosResponse();
        try {
            // TODO: and this is how you should retrieve the ID (or any other parameter you need); no need to add another parameter in the "handle" function
            GetLoansLoanIdResponse result = loansApi.retrieveLoan(intent.getParameterAsLong("ID"), false);
            response.setContent(result.toString());
        } catch (ApiException e) {
            log.error("Something wrong in loans status, ", e);
            response.setContent(e.getMessage());
        }

        return response;
    }
}
