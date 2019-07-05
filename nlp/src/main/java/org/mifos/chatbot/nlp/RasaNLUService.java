package org.mifos.chatbot.nlp;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.NLPService;
import org.mifos.chatbot.core.model.Intent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class RasaNLUService implements NLPService {

    @Value("${rasa.nlu.service.url}")
    String url;

    @Override
    public Intent[] recognize(String input) throws IOException {
        return null;
    }
}
