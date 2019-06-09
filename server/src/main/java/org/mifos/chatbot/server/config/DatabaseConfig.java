package org.mifos.chatbot.server.config;

import org.mifos.chatbot.database.DatabaseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    public DatabaseHandler databaseHandler() {
        return new DatabaseHandler();
    }

}
