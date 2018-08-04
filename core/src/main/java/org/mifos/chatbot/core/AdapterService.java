package org.mifos.chatbot.core;

import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;

import java.util.List;

public interface AdapterService {
    List<MifosResponse> handle(Intent intent, Long id);

    List<MifosResponse> handle(String input);
}
