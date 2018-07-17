package org.mifos.chatbot.core.model;

import java.util.ArrayList;

/**
 * This class will handle the request from this Chatbot project to Mifos database
 */

public class MifosRequest {
    private ArrayList<Intent> intents;

    public ArrayList<Intent> getIntents() {
        return this.intents;
    }

    public void setIntents(ArrayList<Intent> intents) {
        this.intents = intents;
    }
}
