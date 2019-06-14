package org.mifos.chatbot.protocol.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramChatService extends TelegramLongPollingBot {

    @Value("${telegram.botUsername}")
    private String botUsername;

    @Value("${telegram.botToken}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("Message received");
            final String messageText = update.getMessage().getText();
            final long senderId = update.getMessage().getChatId();
            sendTextMessage(senderId, "Received message: " + messageText);
        }
    }

    private void sendTextMessage(long recipientId, String text) {
        SendMessage message = new SendMessage()
                .setChatId(recipientId)
                .setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
