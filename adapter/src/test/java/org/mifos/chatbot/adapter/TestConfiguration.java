/**
 * Copyright 2018 Dingfan Zhao
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mifos.chatbot.adapter;

import lombok.extern.slf4j.Slf4j;
import org.mifos.chatbot.client.ApiClient;
import org.mifos.chatbot.client.api.*;
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

    @Bean
    public ClientApi clientApi() {
        return new ClientApi(apiClient());
    }

    @Bean
    public LoanProductsApi loanProductsApi() {
        return new LoanProductsApi(apiClient());
    }

    @Bean
    public SavingsAccountApi savingsAccountApi() {
        return new SavingsAccountApi(apiClient());
    }
}
