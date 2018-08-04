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
