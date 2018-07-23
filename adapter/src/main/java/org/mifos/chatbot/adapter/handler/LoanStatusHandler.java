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
        String[] keywords = intent.getKeyword().split(" ");
        for (String keyword : keywords) {
            if(!INTENT_KEYWORD.contains(keyword)) {
                return false;
            }
        }
//        return INTENT_KEYWORD.equals(intent.getKeyword());
        return true;
    }

    @Override
    public MifosResponse handle(Intent intent) {
        MifosResponse response = new MifosResponse();

        // SomeLoanStatusObject result = apiClient.execute(); // TODO: do your thing here and call the loan status api!
        // response.setContent(result.toString());
//        response.setContent(INTENT_KEYWORD + ": NOT YET IMPLEMENTED!!!");


        return response;
    }
}
