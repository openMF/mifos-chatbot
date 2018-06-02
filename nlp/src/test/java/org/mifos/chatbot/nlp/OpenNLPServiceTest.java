package org.mifos.chatbot.nlp;

import org.junit.Test;
import org.mifos.chatbot.core.NLPService;
import org.mifos.chatbot.core.model.Intent;
import org.springframework.beans.factory.annotation.Autowired;

public class OpenNLPServiceTest {
    @Autowired
    private NLPService nlpService;

    @Test
    public void recognizeTest() {
        Intent result = nlpService.recognize("");
    }
}
