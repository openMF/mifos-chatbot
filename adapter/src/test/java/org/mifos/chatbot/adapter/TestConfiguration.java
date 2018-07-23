package org.mifos.chatbot.adapter;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.client.ApiClient;
import org.mifos.chatbot.client.api.HolidaysApi;
import org.mifos.chatbot.client.api.LoansApi;
import org.mifos.chatbot.core.model.MifosSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
@ComponentScan("org.mifos.chatbot")
public class TestConfiguration {

    @Autowired
    private MifosSettings settings;

    @Bean
    public ApiClient apiClient() {
        // TODO: improve this
        return new ApiClient()
            .setBasePath(settings.getApiUrl());
    }

    @Bean
    public HolidaysApi holidaysApi() {
        return new HolidaysApi(apiClient());
    }

    @Bean
    public LoansApi loansApi() {
        return new LoansApi(apiClient());
    }
}
