package org.mifos.chatbot.adapter;

import org.mifos.chatbot.core.model.Intent;

class SmallTalk {
    private static final String[] SMALL_TALK_INTENTS = {"hello", "bye"};

    static boolean isSmallTalkRequest(Intent intent) {
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
