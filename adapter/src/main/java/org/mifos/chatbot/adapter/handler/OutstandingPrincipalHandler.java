package org.mifos.chatbot.adapter.handler;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OutstandingPrincipalHandler extends BaseIntentHandler {
    private static final String INTENT_KEYWORD = "outstandingPrincipal";

    @Override
    public Boolean canHandle(Intent intent) {
        // TODO: improve if necessary
        return INTENT_KEYWORD.equals(intent.getKeyword());
    }

    @Override
    public MifosResponse handle(Intent intent) {
        // TODO: implement this
        MifosResponse response = new MifosResponse();
        response.setContent(INTENT_KEYWORD + ": NOT YET IMPLEMENTED!!!");

        return response;
    }
}
