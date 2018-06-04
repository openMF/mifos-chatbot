package org.mifos.chatbot.nlp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OpenNLPTrainerTest {
    @Test
    public void trainerTest() {
        OpenNLPModelTrainer trainer = new OpenNLPModelTrainer();
        Assert.assertTrue(trainer.train());
    }
}
