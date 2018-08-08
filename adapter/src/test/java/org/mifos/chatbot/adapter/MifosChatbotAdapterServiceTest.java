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

    /**
     * During unit test, put Id stubs
     */
    @Test
    public void loanStatus() {
        Intent loanStatusIntent = new Intent("loanStatus");
        loanStatusIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(loanStatusIntent);

        //
        for(MifosResponse response : responses) {
            log.info("Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void getAllHolidays() {
        Intent getAllHolidaysIntent = new Intent("getAllHolidays");
        getAllHolidaysIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(getAllHolidaysIntent);

        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    // TODO: add more tests
    @Test
    public void nextDueDateTest() {
        Intent nextDueDateIntent = new Intent("checkmynext duedate");
        nextDueDateIntent.addParameter("ID", 2L);

        List<MifosResponse> responses = chatbotAdapterService.handle(nextDueDateIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void arrearDayTest() {
        Intent arrearDayIntent = new Intent("arrearday");
        arrearDayIntent.addParameter("ID", 2L);

        List<MifosResponse> responses = chatbotAdapterService.handle(arrearDayIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void dueInterestTest() {
        Intent dueInterestIntent = new Intent("dueInterest");
        dueInterestIntent.addParameter("ID", 2L);

        List<MifosResponse> responses = chatbotAdapterService.handle(dueInterestIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void firstRepaymentDateTest() {
        Intent firstRepaymentDateIntent = new Intent("firstrepaymentdate");
        firstRepaymentDateIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(firstRepaymentDateIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void clientActivationDateTest() {
        Intent clientActivationDateIntent = new Intent("clientactivationdate");
        clientActivationDateIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(clientActivationDateIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void disbursementAmountTest() {
        Intent disbursementAmountIntent = new Intent("disbursementamount");
        disbursementAmountIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(disbursementAmountIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void disbursementDateTest() {
        Intent disbursementDateIntent = new Intent("disbursementDate");
        disbursementDateIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(disbursementDateIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void disbursementInterestTest() {
        Intent disbursementInterestIntent = new Intent("disbursementInterest");
        disbursementInterestIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(disbursementInterestIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void maturityDateTest() {
        Intent maturityDateIntent = new Intent("maturityDate");
        maturityDateIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(maturityDateIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void outstandingInterestTest() {
        Intent outstandingInterestIntent = new Intent("outstandingInterest");
        outstandingInterestIntent.addParameter("ID", 2L);

        List<MifosResponse> responses = chatbotAdapterService.handle(outstandingInterestIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void outstandingPrincipalTest() {
        Intent outstandingPrincipalIntent = new Intent("outstandingPrincipal");
        outstandingPrincipalIntent.addParameter("ID", 2L);

        List<MifosResponse> responses = chatbotAdapterService.handle(outstandingPrincipalIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void overdueInterestTest() {
        Intent overdueInterestIntent = new Intent("overdueInterest");
        overdueInterestIntent.addParameter("ID", 2L);

        List<MifosResponse> responses = chatbotAdapterService.handle(overdueInterestIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void overduePrincipalTest() {
        Intent overduePrincipalIntent = new Intent("overduePrincipal");
        overduePrincipalIntent.addParameter("ID", 2L);

        List<MifosResponse> responses = chatbotAdapterService.handle(overduePrincipalIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void penaltyChargeTest() {
        Intent penaltyChargeIntent = new Intent("penaltyCharge");
        penaltyChargeIntent.addParameter("ID", 2L);

        List<MifosResponse> responses = chatbotAdapterService.handle(penaltyChargeIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void previousPaymentDateTest() {
        Intent previousPaymentDateIntent = new Intent("previousPaymentDate");
        previousPaymentDateIntent.addParameter("ID", 2L);

        List<MifosResponse> responses = chatbotAdapterService.handle(previousPaymentDateIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void interestRateTest() {
        Intent interestRateIntent = new Intent("interestRate");
        interestRateIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(interestRateIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void loanTermTest() {
        Intent loanTermIntent = new Intent("loanTerm");
        loanTermIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(loanTermIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void maxAllowedAmountTest() {
        Intent maxAllowedAmountIntent = new Intent("maxAllowedAmount");
        maxAllowedAmountIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(maxAllowedAmountIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void minAllowedAmountTest() {
        Intent minAllowedAmountIntent = new Intent("minAllowedAmount");
        minAllowedAmountIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(minAllowedAmountIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void repaymentNumberTest() {
        Intent repaymentNumberIntent = new Intent("repaymentNumber");
        repaymentNumberIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(repaymentNumberIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void savingActivationDateTest() {
        Intent savingActivationDateIntent = new Intent("savingActivationDate");
        savingActivationDateIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(savingActivationDateIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void savingBalanceTest() {
        Intent savingBalanceIntent = new Intent("savingBalance");
        savingBalanceIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(savingBalanceIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void savingInterestTest() {
        Intent savingInterestIntent = new Intent("savingInterest");
        savingInterestIntent.addParameter("ID", 1L);

        List<MifosResponse> responses = chatbotAdapterService.handle(savingInterestIntent);
        for(MifosResponse response : responses) {
            log.info(">>>> Handler response: \n{}", response.getContent());
        }
    }

    @Test
    public void settings() {
        log.warn("This is the API url: {}", settings.getApiUrl()); // just to demonstrate how dependency injected properties are working
    }
}
