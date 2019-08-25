/**
 * Copyright 2018 Dingfan Zhao
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mifos.chatbot.adapter.handler.loan;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.Configuration;
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
    private static final String[] INTENT_KEYWORDS = {"due_date"};

    @Autowired
    private LoansApi loansApi;

    @Override
    public Boolean canHandle(Intent intent) {
        for(String intent_keyword : INTENT_KEYWORDS) {
            if (!intent.getKeyword().toLowerCase().contains(intent_keyword.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public MifosResponse handle(Intent intent) {
        loansApi.setApiClient(Configuration.getDefaultApiClient());
        MifosResponse response = new MifosResponse();
        try {
            GetLoansLoanIdResponse result = loansApi.retrieveLoan(intent.getParameterAsLong("ID"), false);

            List<Long> overDueSinceDate = result.getSummary().getOverdueSinceDate();
            String frequency = result.getRepaymentFrequencyType().getValue();
            int numOfPeriod = result.getRepaymentEvery();

            String date = getNextDueDate(overDueSinceDate, frequency, numOfPeriod);
            if (date != null) {
                response.setContent(date);
            } else {
                response.setContent("No data found for the given id.");
            }

        } catch (ApiException e) {
            log.error(e.toString(), e);
            response.setContent(e.getMessage());
        }
        return response;
    }

    // TODO: extracted as a util function
    private String getNextDueDate(List<Long> overDueSinceDate, String frequency, int numOfPeriod) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("%04d", overDueSinceDate.get(0)));
            sb.append(String.format("%02d", overDueSinceDate.get(1)));
            sb.append(String.format("%02d", overDueSinceDate.get(2)));
            String dateStr = sb.toString();
            FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
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
