package org.mifos.chatbot.client;

import io.swagger.client.ApiClient;
import org.mifos.chatbot.core.MifosService;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosRequest;
import org.mifos.chatbot.core.model.MifosResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class will handle the API interactions with Mifos
 *
 * The MifosRequest may contain different kinds of requests
 * like requests for loan, savings, loan products, group information, branch information
 */
public class MifosAPIService implements MifosService {
    private final String basePath = "localhost:8443/fineract-provider/api/v1";
    private ConcurrentHashMap<String, Object> APIReference = new ConcurrentHashMap<>();
    private ApiClient apiClient;

    Logger logger = LoggerFactory.getLogger(MifosAPIService.class);

    @Override
    public MifosResponse process(MifosRequest request) {
        // Here is the code provided by Swagger that made the API call to Mifos
        System.out.println("This is to print out the request content");

        /* Basically, the steps of processing a MifosRequest sent from NLP engine
            0. Generate a mapping between Intent and API call (maybe a XML or JSON file in the resource folder, use ClassLoader to load it)
            1. Setup the connection with the Fineract local instance
            2. Consume the request, extract the Intent in the request
            3. Find the specific API to call and get the response
            4. Wrap and generate it in the MifosResponse
                a. The MifosResponse still need further enhancement about its structure
        */

        // try to integrate with Slack
        // think about the protocol

        // 0. Generate mapping between Intent and API call
        // 1. Setup connection with Fineract local instance
        try {
            setup();
        } catch (Exception e) {
            logger.error("Something wrong during setup process : " + e);
        }

        // 2. Consume the request, extract the Intent in the request
        ArrayList<Intent> Intents = request.getIntents();

        // 3. Find the specific API to call and get the response
        apiClient.execute();

        // continue and try to figure out to create one user case
        // integrate with Slack, which means sending query from slack and get back the response to slack bot
        return null;
    }

    private void setup() throws Exception {
        apiClient = new ApiClient();
        logger.info("Current base path is : " + apiClient.getBasePath());
        apiClient.setBasePath(basePath);
        logger.info("The base bath is set to \"" + apiClient.getBasePath() + "\"");

//        File file = ClassLoader.getSystemClassLoader().getResource("mapping.xml");
        File file = new File("../../resources/mapping.xml");
        InputStream is = new FileInputStream(file);
        Reader reader = new InputStreamReader(is, "UTF-8");
        InputSource inputSource = new InputSource(reader);
        inputSource.setEncoding("UTF-8");

        // I plan to use samParser to parse the XML file in resource folder
        saxParser.parse(inputSource, handler);
    }
}
