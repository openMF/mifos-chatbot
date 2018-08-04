package org.mifos.chatbot.core;

import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;

public interface IntentHandler {
    Boolean canHandle(Intent intent);

    MifosResponse handle(Intent intent, Long id);
}
