package org.mifos.chatbot.adapter.handler;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.LoansApi;
import org.mifos.chatbot.client.model.GetLoansLoanIdResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class LoanStatusHandler extends BaseIntentHandler {
    private static final String INTENT_KEYWORD = "loanStatus";

    @Autowired
    private LoansApi loansApi;

    @Override
    public Boolean canHandle(Intent intent) {
        // TODO: improve if necessary
        String[] keywords = intent.getKeyword().split(" ");
        for (String keyword : keywords) {
            if(!INTENT_KEYWORD.contains(keyword)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public MifosResponse handle(Intent intent) {
        MifosResponse response = new MifosResponse();

        // SomeLoanStatusObject result = apiClient.execute(); // TODO: do your thing here and call the loan status api!
        try {
            GetLoansLoanIdResponse result = loansApi.retrieveLoan(1L, false);
            response.setContent(result.toString());
        } catch (ApiException e) {
            log.error("Something wrong in loans status, ", e);
            response.setContent(e.getMessage());
        }

        return response;
    }
}
