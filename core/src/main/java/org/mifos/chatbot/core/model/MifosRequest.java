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
