package org.mifos.chatbot.core.model;

// the data holder
public class Intent {
    // Intent represents a mapping between what a user says and what action your software should take.
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
}
