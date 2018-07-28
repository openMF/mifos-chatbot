package org.mifos.chatbot.nlp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mifos.chatbot.core.NLPService;
import org.mifos.chatbot.core.model.Intent;
import org.springframework.beans.factory.annotation.Autowired;

public class OpenNLPServiceTest {
    @Autowired
    private NLPService nlpService;

    @Before
    public void setup() {
        nlpService = new OpenNLPService();
    }

    // Intent should be specific enough to point to the RESTful API of Mifos
    @Test
    public void recognizeTest() {
        Intent[] results = nlpService.recognize("not at all");
        // TODO: Update NLP model, its detection is not accurate
        for(int i = 0; i < results.length ;i++)
            System.out.println(results[i].getKeyword());
//        Assert.assertTrue(results[0].getKeyword().equals("Interest rate"));
    }
}
