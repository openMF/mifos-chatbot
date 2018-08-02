package org.mifos.chatbot.adapter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.mifos.chatbot.core.model.MifosSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class MifosChatbotAdapterServiceTest {

    @Autowired
    private MifosChatbotAdapterService chatbotAdapterService;

    @Autowired
    private MifosSettings settings;

    @Test
    public void loanStatus() {
        Intent loanStatusIntent = new Intent("loanStatus");

        List<MifosResponse> responses = chatbotAdapterService.handle(loanStatusIntent);

        //
        for(MifosResponse response : responses) {
            log.info("Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void getAllHolidays() {
        Intent getAllHolidaysIntent = new Intent("getAllHolidays");

        List<MifosResponse> responses = chatbotAdapterService.handle(getAllHolidaysIntent);

        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    // TODO: add more tests
    @Test
    public void nextDueDateTest() {
        Intent nextDueDateIntent = new Intent("checkmynext duedate");

        List<MifosResponse> responses = chatbotAdapterService.handle(nextDueDateIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void arrearDayTest() {
        Intent arrearDayIntent = new Intent("arrearday");

        List<MifosResponse> responses = chatbotAdapterService.handle(arrearDayIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void dueInterestTest() {
        Intent dueInterestIntent = new Intent("dueInterest");

        List<MifosResponse> responses = chatbotAdapterService.handle(dueInterestIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void firstRepaymentDateTest() {
        Intent firstRepaymentDateIntent = new Intent("firstrepaymentdate");

        List<MifosResponse> responses = chatbotAdapterService.handle(firstRepaymentDateIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void settings() {
        log.warn("This is the API url: {}", settings.getApiUrl()); // just to demonstrate how dependency injected properties are working
    }
}
