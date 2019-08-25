package org.mifos.chatbot.nlp;

import com.joestelmach.natty.Parser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mifos.chatbot.core.NLPService;
import org.mifos.chatbot.core.model.Intent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class RasaNLUService implements NLPService {

    @Value("${rasa.nlu.service.url}")
    String url;

    @Override
    public Intent recognize(String input) throws IOException {
        JSONObject payload = createPayload(input);
        HttpResponse rasaNLUResponse = getResponseFromRasaNLU(payload);
        String result = getResult(rasaNLUResponse);
        JSONObject resultJSON;
        try {
            JSONParser jsonParser = new JSONParser();
            resultJSON = (JSONObject) jsonParser.parse(result);
        } catch (ParseException e) {
            log.error(e.toString());
            resultJSON = null;
        }
            return getIntent(resultJSON, input);
    }

    private Intent getIntent(JSONObject resultJSON, String input) {
        if (resultJSON == null) {
            return null;
        }
        List<Intent> resultIntents = new ArrayList<>();
        String subIntent = resultJSON.get("intent").toString().split("\"")[5];
        resultIntents.add(new Intent(subIntent));
        Intent intent = null;
        for (Intent resultIntent : resultIntents) {
            intent = resultIntent;
            intent.addParameter("ID", findId(input));
            intent.addParameter("Date", findDate(input));
        }
        log.info("Found {} intents", intent.getKeyword());
        return intent;
    }

    private JSONObject createPayload(String input) {
        JSONObject payload = new JSONObject();
        payload.put("text", input);
        return payload;
    }

    private HttpResponse getResponseFromRasaNLU(JSONObject payload) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(payload.toString(), ContentType.APPLICATION_JSON));
        return client.execute(post);
    }

    private String getResult(HttpResponse RasaNLUResponse) throws IOException {
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(RasaNLUResponse.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        log.info("Result from RasaNLU {}", result.toString());
        return result.toString();
    }

    private String findDate(String input) throws IndexOutOfBoundsException {
        try {
            List<Date> dates = new Parser().parse(input).get(0).getDates();
            Date date = dates.get(0);
            return (date.getMonth() + 1) + "-" + date.getDate() + "-" + (date.getYear() + 1900);
        } catch (Exception e) {
            return "";
        }
    }

    private long findId(String input) {
        long id = 1L;
        for (String token : input.split(" ")) {
            try {
                id = Long.parseLong(token);
            } catch (NumberFormatException e) {
            }
        }
        return id;
    }

}
