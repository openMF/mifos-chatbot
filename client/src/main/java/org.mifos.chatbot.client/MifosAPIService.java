package org.mifos.chatbot.client;

import org.mifos.chatbot.client.ApiClient;
import org.mifos.chatbot.core.MifosService;
import org.mifos.chatbot.core.model.Intent;
import org.mifos.chatbot.core.model.MifosRequest;
import org.mifos.chatbot.core.model.MifosResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class will handle the API interactions with Mifos
 *
 * The MifosRequest may contain different kinds of requests
 * like requests for loan, savings, loan products, group information, branch information
 *
 * Basically, the steps of processing a MifosRequest sent from NLP engine
 *      0. Generate a mapping between Intent and API call (maybe a XML or JSON file in the resource folder, use ClassLoader to load it)
 *      1. Setup the connection with the Fineract local instance
 *      2. Consume the request, extract the Intent in the request
 *      3. Find the specific API to call and get the response
 *      4. Wrap and generate it in the MifosResponse
 *          a. The MifosResponse still need further enhancement about its structure
 */
@Deprecated // TODO: find a different solution with an adapter module
public class MifosAPIService implements MifosService {
    private final String basePath = "localhost:8443/fineract-provider/api/v1";
    private ConcurrentHashMap<String, Object> APIReference;
    private ApiClient apiClient;

    Logger logger = LoggerFactory.getLogger(MifosAPIService.class);

    public MifosAPIService() {
        APIReference = new ConcurrentHashMap<>();

        // 0. Generate mapping between Intent and API call
        // 1. Setup connection with Fineract local instance
        try {
            setup();
        } catch (Exception e) {
            logger.error("Something wrong during setup process : " + e);
        }
    }

    @Override
    public MifosResponse process(MifosRequest request) {
        // Here is the code provided by Swagger that made the API call to Mifos
        System.out.println("This is to print out the request content");

        // try to integrate with Slack
        // think about the protocol

        // 2. Consume the request, extract the Intent in the request
        ArrayList<Intent> intents = request.getIntents();
        String urlRequest = (String) APIReference.get(intents.get(1).getKeyword());

        // 3. Find the specific API to call and get the response
        // apiClient.execute();

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

        System.out.println("current path:" + System.getProperty("user.dir"));
        File file = new File("src/main/resources/mapping.xml");

        InputStream is = new FileInputStream(file);
        Reader reader = new InputStreamReader(is, "UTF-8");
        InputSource inputSource = new InputSource(reader);
        inputSource.setEncoding("UTF-8");

        // I plan to use samParser to parse the XML file in resource folder
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {
            ConcurrentHashMap<String, String> tempMapping = new ConcurrentHashMap<>();
            boolean startMapping = false;
            public void startElement(String uri, String localName,String qName,
                                     Attributes attributes) {
                System.out.println("start element: " + qName);
                if(qName.equalsIgnoreCase("request")) {
                    startMapping = true;
                    tempMapping = new ConcurrentHashMap<>();
                }

                System.out.println(qName);
                if(startMapping) {
                    tempMapping.put(qName, null);
                }
            }

            public void characters(char ch[], int start, int length) throws SAXException {
                if(tempMapping.containsKey("name")) {
                    tempMapping.put("name", new String(ch, start, length));
                }
            }

            public void endElement(String uri, String localName, String qName)
                    throws SAXException {
                if(qName.equalsIgnoreCase("request")) {
                    startMapping = false;
                    APIReference.put(qName, tempMapping);
                }
            }
        };

        parser.parse(inputSource, handler);
    }
}
