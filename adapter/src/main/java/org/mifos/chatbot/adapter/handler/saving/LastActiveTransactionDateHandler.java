package org.mifos.chatbot.adapter.handler.saving;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.adapter.handler.HandlerUtils;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.LoanProductsApi;
import org.mifos.chatbot.client.api.SavingsAccountApi;
import org.mifos.chatbot.client.model.GetLoanProductsProductIdResponse;
import org.mifos.chatbot.client.model.GetSavingsAccountsAccountIdResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LastActiveTransactionDateHandler extends BaseSavingIntentHandler {
    private static final String INTENT_KEYWORD = "interestRate";

//    @Autowired
//    LoanProductsApi loanProductsApi;

    @Autowired
    SavingsAccountApi savingsAccountApi;
    @Override
    public Boolean canHandle(Intent intent) {
        return intent.getKeyword().toLowerCase().contains(INTENT_KEYWORD.toLowerCase());
    }

    @Override
    public MifosResponse handle(Intent intent) {
        MifosResponse response = new MifosResponse();
        try {
            GetSavingsAccountsAccountIdResponse result = savingsAccountApi.retrieveOne(1L, false, "default");
            HandlerUtils.convertListToDate(result.getTimeline().getActivatedOnDate());
        } catch (ApiException e) {
            log.info("Error", e);
        }

        return response;
    }
}
