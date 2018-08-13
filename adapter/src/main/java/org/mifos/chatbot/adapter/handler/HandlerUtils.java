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
package org.mifos.chatbot.adapter.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
public class HandlerUtils {

    public static Date convertListToDate(List<Long> list) {
        if(list == null) {
            log.warn("The date is empty. ");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%04d", list.get(0)));
        sb.append(String.format("%02d", list.get(1)));
        sb.append(String.format("%02d", list.get(2)));
        String dateStr = sb.toString();
        FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
        try {
            return fdf.parse(dateStr);
        } catch (ParseException e) {
            log.info("Exception in HandlerUtils : ", e);
        }

        return new Date();
    }
}
