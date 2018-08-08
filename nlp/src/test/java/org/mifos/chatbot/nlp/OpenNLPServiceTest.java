package org.mifos.chatbot.nlp;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mifos.chatbot.core.NLPService;
import org.mifos.chatbot.core.model.Intent;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class OpenNLPServiceTest {
    private OpenNLPService nlpService;

    @Before
    public void setup() {
        nlpService = new OpenNLPService();
    }

    // Intent should be specific enough to point to the RESTful API of Mifos
    @Test
    public void recognizeTest() {
        Intent[] results = nlpService.recognize("check my next due date, date is 2017/09/13");
        // TODO: Update NLP model, its detection is not accurate
        for(int i = 0; i < results.length ;i++) {
            log.info(results[i].getKeyword());
        }
        Assert.assertTrue(hasKeyword(results, "next due date"));
    }

    private Boolean hasKeyword(Intent[] intents, String keyword) {
        for(Intent intent : intents) {
            if(intent.getKeyword().equalsIgnoreCase(keyword))
                return true;
        }

        return false;
    }
}
