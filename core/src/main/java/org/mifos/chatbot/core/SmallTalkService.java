package org.mifos.chatbot.core;

import org.mifos.chatbot.core.model.Intent;

public interface SmallTalkService {
    static final String[] 2SMALL_TALK_INTENTS = {
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

    static final String[][] RESPONSE = {
            {"I am mifos Assistant, here to assist you.", "I am mifos Assistant, i can solve your queries like your loan interest or principal overdue, etc."},
            {"I'm a relatively new bot", "I'm still a newbie in human years"},
            {"I'm sorry but i'm constantly perfecting my domain.", "i am sorry you feel that way. I am still learning. Please provide my developers your valuable feedback to improve me."},
            {"You can ask me about loan, loan product, savings queries like 'whats my interest rate' or 'look my loan status'.", "You can ask me about loan, loan product, savings queries like 'what's my due principal' or 'what is my due interest'."},
            {"I am a bot who loves to chat.", "Yes i'm a chatbot."},
            {"I'm glad you think so.", "Thanks.", "I appreciate it. Thank you."},
            {"I live somewhere in clouds, you can find me when you are on flight.", "I wish I knew where exactly.", "I can see sky and clouds all around from here."},
            {"Of course.", "100 percent sure.", "Yes."},
            {"That's what I'm here for.", "My pleasure to help you.", "Always ready."},
            {"Thanks :)", "I appreciate it.", "My pleasure."},
            {"Thank you for the complement :)", "Happy to help"},
            {"Bye Bye", "I'll see you later", "Bye, Have a nice day", "Goodbye", "Nice to talk to you!", "Nice to meet you!", "I'll miss you :'("},
            {"Good evening", "How is your day going?", "How's your day been?", ":) you too"},
            {"Good morning", "Good morning! How are you today?", "Have a exciting day"},
            {"Good night", "Talk to you soon!", "Have a great sleep", "Zzzz -_-"},
            {"Hello", "Hola", "Hi", "Hi there, friend!", "Hey!"},
            {"Oh no, how can i help?", "I am sorry to hear that."},
            {"I'm happy you're happy", "Great", "Me too :)"},
            {"What happen", "Oh no, It may not be much but let me know if there is anything i can do", "Sorry to hear that."},
            {"Hope I'm doing well, You're welcome to test me as often as you want.", "Great, let my developers know any feedback.", "Feel free to test me as much as you want and provide my developers feedback to help me improve :)"}
    };

    boolean isSmallTalkRequest(Intent intent);

}
