package org.mifos.chatbot.adapter;

import org.mifos.chatbot.core.model.Intent;

class SmallTalk {
    private static final String[] SMALL_TALK_INTENTS = {
            "acquaintance",
            "age",
            "bad_bot",
            "help",
            "chatbot",
            "good_bot",
            "origin",
            "sure",
            "thank_you",
            "welcome",
            "well_done",
            "greet_bye",
            "greet_goodevening",
            "greet_goodmorning",
            "greet_goodnight",
            "greet_hello",
            "angry",
            "happy",
            "sad",
            "testing"
    };

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
