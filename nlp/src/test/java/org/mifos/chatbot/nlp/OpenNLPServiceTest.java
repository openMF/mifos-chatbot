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

    @Test
    public void recognizeTest() {
        Intent[] results = nlpService.recognize("Check my loan fee. check my Interest rate");
//        System.out.println(results[0].getKeyword());
        Assert.assertTrue(results[0].getKeyword().equals("loan fee"));
    }
}
