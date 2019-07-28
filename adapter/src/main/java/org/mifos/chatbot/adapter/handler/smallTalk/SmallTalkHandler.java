package org.mifos.chatbot.adapter.handler.smallTalk;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class SmallTalkHandler extends BaseSmallTalkHandler {
    private static final String[] INTENT_KEYWORDS = {"greet_bye", "greet_goodevening", "greet_goodmorning", "greet_goodnight", "greet_hello"};
    private static final String[][] GREET_RESPONSE = {
            {"Bye Bye", "I'll see you later", "Bye, Have a nice day", "Goodbye", "Nice to talk to you!", "Nice to meet you!", "I'll miss you :'("},
            {"Good evening", "How is your day going?", "How's your day been?", ":) you too"},
            {"Good morning", "Good morning! How are you today?", "Have a exciting day"},
            {"Good night", "Talk to you soon!", "Have a great sleep", "Zzzz -_-"},
            {"Hello", "Hola", "Hi", "Hi there, friend!", "Hey!"}
    };


    @Override
    public Boolean canHandle(Intent intent) {
        boolean find = false;
        for(String intent_keyword : INTENT_KEYWORDS) {
            if (intent.getKeyword().toLowerCase().equals(intent_keyword.toLowerCase())) {
                find = true;
            }
        }
        return find;
    }

    @Override
    public MifosResponse handle(Intent intent) {
        String intentKeyword = intent.getKeyword().toLowerCase();
        MifosResponse mifosResponse = new MifosResponse();
        for(int i=0; i<INTENT_KEYWORDS.length; i++) {
            if(intentKeyword.equals(INTENT_KEYWORDS[i])) {
                mifosResponse.setContent((GREET_RESPONSE[i][new Random().nextInt(GREET_RESPONSE[i].length)]));
                return mifosResponse;
            }
        }
        return null;
    }
}
