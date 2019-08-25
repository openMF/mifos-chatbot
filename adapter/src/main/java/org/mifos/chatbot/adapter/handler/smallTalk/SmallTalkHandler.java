package org.mifos.chatbot.adapter.handler.smallTalk;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.core.SmallTalkService;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class SmallTalkHandler extends BaseSmallTalkHandler implements SmallTalkService {

    @Override
    public Boolean canHandle(Intent intent) {
        boolean find = false;
        for (String intent_keyword : SMALL_TALK_INTENTS) {
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
        for (int i = 0; i < SMALL_TALK_INTENTS.length; i++) {
            if (intentKeyword.equals(SMALL_TALK_INTENTS[i])) {
                mifosResponse.setContent((RESPONSE[i][new Random().nextInt(RESPONSE[i].length)]));
                return mifosResponse;
            }
        }
        return null;
    }

    @Override
    public boolean isSmallTalkRequest(Intent intent) {
        if(intent == null) {
            return false;
        }
        for(String smallTalkIntents : SMALL_TALK_INTENTS) {
            if (intent.getKeyword().contains(smallTalkIntents.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
