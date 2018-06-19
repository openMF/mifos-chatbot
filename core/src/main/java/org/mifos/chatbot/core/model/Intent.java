package org.mifos.chatbot.core.model;

// the data holder
public class Intent {
    // Intent represents a mapping between what a user says and what action your Chatbot should take.

    // provide the confidence level of the recognition
    // When confidence level is lower than the threshold, then return error feedback
    private String keyword;

    public Intent(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // API category, function and parameters

    // find mifos working on my own machine

    // design mifos API client
}
