package org.mifos.chatbot.adapter;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.AdapterService;
import org.mifos.chatbot.core.IntentHandler;
import org.mifos.chatbot.core.NLPService;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MifosChatbotAdapterService implements AdapterService {
    @Autowired
    private NLPService openNLPService;

    @Autowired
    private List<IntentHandler> handlers;

    public List<MifosResponse> handle(Intent intent) {
        return handlers
            .stream()
            .filter(handler -> handler.canHandle(intent))
            .map(handler -> handler.handle(intent))
            .collect(Collectors.toList());
    }

    public List<MifosResponse> handle(String input) {
        List<MifosResponse> results = new ArrayList<>();
        Intent[] intents = openNLPService.recognize(input);
        for(Intent intent : intents) {
            results.addAll(handle(intent));
        }
        return results;
    }

    /*


      [Slack/Viber/XMPP/IRC/Skype] -> NLP -> Intent1, Intent2, Intent3 -> AdapterService(Intent) -> responses -> [Slack/Viber/XMPP/IRC/Skype]


     */
}
