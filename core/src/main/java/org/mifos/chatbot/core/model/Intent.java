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

import java.util.HashMap;
import java.util.Map;

// the data holder
public class Intent {
    // Intent represents a mapping between what a user says and what action your Chatbot should take.

    // provide the confidence level of the recognition
    // When confidence level is lower than the threshold, then return error feedback
    private String keyword;

    private Map<String, Object> parameters = new HashMap<>();

    public Intent(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(String key, Object value) {
        this.parameters.put(key, value);
    }

    public Long getParameterAsLong(String key) {
        return Long.valueOf(this.parameters.get(key).toString());
    }

    public Integer getParameterAsInteger(String key) {
        return Integer.valueOf(this.parameters.get(key).toString());
    }

    public String getParameterAsString(String key) {
        return this.parameters.get(key).toString();
    }

    // API category, function and parameters

    // find mifos working on my own machine

    // design mifos API client
}
