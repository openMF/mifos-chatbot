package org.mifos.chatbot.nlp;

import org.mifos.chatbot.core.NLPService;
import org.mifos.chatbot.core.model.Intent;

public class OpenNLPServiceTest {
    @Autowired
    private NLPService nlpService;

    @Test
    public void recognizeTest() {
        Intent result = nlpService.recognize("");
    }
}
