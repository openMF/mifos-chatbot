package org.mifos.chatbot.adapter.handler.loan;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.api.HolidaysApi;
import org.mifos.chatbot.client.model.GetHolidaysResponse;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GetAllHolidaysHandler extends BaseLoanIntentHandler {
    private static final String[] INTENT_KEYWORDS = {"get", "All", "Holidays"};

    @Autowired
    private HolidaysApi holidaysApi;

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
        MifosResponse response = new MifosResponse();

        // TODO: figure out if we need these parameters, which ones are required
//        Long officeId = 1L; // probably you have to detect to which office the user belongs or this is just set statically
        String fromDate = null;
        String toDate = null;
        String locale = null;
        String dateFormat = null;

        try {
            List<GetHolidaysResponse> result = holidaysApi.retrieveAllHolidays(
                    intent.getParameterAsLong("ID"),
                    fromDate,
                    toDate,
                    locale,
                    dateFormat);

            response.setContent(result.toString());

        } catch (ApiException e) {
            log.error(e.toString(), e);
            response.setContent(e.getMessage());
        }

        return response;
    }
}
