package org.mifos.chatbot.adapter.handler.loanproduct;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.LoanProductsApi;
import org.mifos.chatbot.client.model.GetLoanProductsProductIdResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoanTermHandler extends BaseLoanProductIntentHandler {
    private static final String[] INTENT_KEYWORDS = {"loan", "Term"};

    @Autowired
    private LoanProductsApi loanProductsApi;

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
            GetLoanProductsProductIdResponse result = loanProductsApi.retrieveLoanProductDetails(intent.getParameterAsLong("ID"));
            String rePaymentFrequency = result.getRepaymentFrequencyType().getValue();
            int numOfRepayments = result.getNumberOfRepayments();

            int days = 0;
            if (rePaymentFrequency.equalsIgnoreCase("weeks")) {
                days = numOfRepayments * 7;
            } else if (rePaymentFrequency.equalsIgnoreCase("months")) {
                days = numOfRepayments * 30;
            } else {
                days = 1;
            }
            String content = String.valueOf(numOfRepayments) + " " + rePaymentFrequency + ", which is approximately " + days + " days";
            response.setContent(content);
        } catch (ApiException e) {
            log.info("Error", e);
            response.setContent(e.getMessage());
        }

        return response;
    }
}
