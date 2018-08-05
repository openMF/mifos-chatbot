package org.mifos.chatbot.server.config;

import org.mifos.chatbot.client.ApiClient;
import org.mifos.chatbot.client.api.*;
import org.mifos.chatbot.core.model.MifosSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfig {
    @Autowired
    private MifosSettings settings;

    @Bean
    public ApiClient apiClient() {
        // TODO: improve this
        return new ApiClient()
            .setBasePath(settings.getApiUrl());
    }

    @Bean
    public HolidaysApi holidaysApi(ApiClient apiClient) {
        return new HolidaysApi(apiClient);
    }

    @Bean
    public LoansApi loansApi(ApiClient apiClient) {
        return new LoansApi(apiClient);
    }

    @Bean
    public ClientApi clientApi(ApiClient apiClient) {
        return new ClientApi(apiClient);
    }

    @Bean
    public LoanProductsApi loanProductsApi(ApiClient apiClient) {
        return new LoanProductsApi(apiClient);
    }

    @Bean
    public SavingsAccountApi savingsAccountApi(ApiClient apiClient) {
        return new SavingsAccountApi(apiClient);
    }
}
