package org.mifos.chatbot.adapter.handler.loan;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.mifos.chatbot.adapter.handler.HandlerUtils;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.LoansApi;
import org.mifos.chatbot.client.model.GetLoansLoanIdResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class FirstRepaymentDateHandler extends BaseLoanIntentHandler {
    private static final String INTENT_KEYWORD = "firstRepaymentDate";

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
            Date date = HandlerUtils.convertListToDate(result.getTimeline().getActualDisbursementDate());
            String frequency = result.getRepaymentFrequencyType().getValue();
            int term = 0;
            if(frequency.equalsIgnoreCase("weeks")) {
                term = result.getRepaymentEvery() * 7;
                date = DateUtils.addDays(date, term);
            } else if(frequency.equalsIgnoreCase("months")) {
                term = result.getRepaymentEvery();
                date = DateUtils.addMonths(date, term);
            }

            response.setContent(date.toString());
        } catch (ApiException e) {
            log.info("Error", e);
            response.setContent(e.getMessage());
        }

        return response;
    }
}
