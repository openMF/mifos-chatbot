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
import org.mifos.chatbot.core.AdapterService;
import org.mifos.chatbot.core.IntentHandler;
import org.mifos.chatbot.core.SmallTalkService;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosResponse;
import org.mifos.chatbot.nlp.RasaNLUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MifosChatbotAdapterService implements AdapterService {
    @Autowired
    private RasaNLUService rasaNLUService;

    @Autowired
    private List<IntentHandler> handlers;

    @Autowired
    private SmallTalkService smallTalkService;

    @Override
    public List<MifosResponse> handle(Intent intent) {
        return handlers
                .stream()
                .filter(handler -> handler.canHandle(intent))
                .map(handler -> handler.handle(intent))
                .collect(Collectors.toList());
    }

    @Override
    public List<MifosResponse> handle(String input) {
        Intent intent = null;
        try {
            intent = rasaNLUService.recognize(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(handle(intent));
    }

    @Override
    public boolean isSmallTalkRequest(String messageText) {
        Intent intent = null;
        try {
            intent = rasaNLUService.recognize(messageText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return smallTalkService.isSmallTalkRequest(intent);
    }
}
