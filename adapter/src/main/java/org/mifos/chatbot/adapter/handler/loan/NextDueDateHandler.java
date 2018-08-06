package org.mifos.chatbot.adapter.handler.loan;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.LoansApi;
import org.mifos.chatbot.client.model.GetLoansLoanIdResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class NextDueDateHandler extends BaseLoanIntentHandler {
    private static final String INTENT_KEYWORD = "DueDate";

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
            // TODO: How to pass the parameters
            GetLoansLoanIdResponse result = loansApi.retrieveLoan(intent.getParameterAsLong("ID"), false);

            // TODO: Correct the response, current response is just for unit test
            List<Long> overDueSinceDate = result.getSummary().getOverdueSinceDate();
            String frequency = result.getRepaymentFrequencyType().getValue();
            int numOfPeriod = result.getRepaymentEvery();

            String date = getNextDueDate(overDueSinceDate, frequency, numOfPeriod);

            response.setContent(date);

        } catch (ApiException e) {
            log.error(e.toString(), e);
            response.setContent(e.getMessage());
        }
        return response;
    }

    private String getNextDueDate(List<Long> overDueSinceDate, String frequency, int numOfPeriod) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%04d", overDueSinceDate.get(0)));
        sb.append(String.format("%02d", overDueSinceDate.get(1)));
        sb.append(String.format("%02d", overDueSinceDate.get(2)));
        String dateStr = sb.toString();
        FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
        try {
            Date date = fdf.parse(dateStr);
            if(frequency.equalsIgnoreCase("weeks")) {
                date = DateUtils.addDays(date, 7);
            } else if(frequency.equalsIgnoreCase(("months"))) {
                date = DateUtils.addMonths(date, 1);
            } else if(frequency.contains("year")) {
                date = DateUtils.addYears(date, 1);
            }
            return date.toString();
        } catch (ParseException e) {
            log.error("Exception when parsing, ", e);
        }

        return null;
    }
}
