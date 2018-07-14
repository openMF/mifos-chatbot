package org.mifos.chatbot.adapter.handler;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoanStatusHandler extends BaseIntentHandler {
    private static final String INTENT_KEYWORD = "loanStatus";

    @Override
    public Boolean canHandle(Intent intent) {
        // TODO: improve if necessary
        return INTENT_KEYWORD.equals(intent.getKeyword());
    }

    @Override
    public MifosResponse handle(Intent intent) {
        MifosResponse response = new MifosResponse();
        response.setContent(INTENT_KEYWORD + ": NOT YET IMPLEMENTED!!!");

        // SomeLoanStatusObject result = apiClient.execute(); // TODO: do your thing here and call the loan status api!
        // response.setContent(result.toString());


        return response;
    }
}
