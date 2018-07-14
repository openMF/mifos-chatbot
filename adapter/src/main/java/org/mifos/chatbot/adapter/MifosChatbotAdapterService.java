package org.mifos.chatbot.adapter;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.IntentHandler;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MifosChatbotAdapterService {
    @Autowired
    private List<IntentHandler> handlers;

    public List<MifosResponse> handle(Intent intent) {
        return handlers
            .stream()
            .filter(handler -> handler.canHandle(intent))
            .map(handler -> handler.handle(intent))
            .collect(Collectors.toList());
    }
}
