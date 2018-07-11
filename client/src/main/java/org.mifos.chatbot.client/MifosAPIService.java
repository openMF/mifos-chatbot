package org.mifos.chatbot.client;

import org.mifos.chatbot.core.MifosService;
import org.mifos.chatbot.core.model.MifosRequest;
import org.mifos.chatbot.core.model.MifosResponse;

/**
 * This class will handle the API interactions with Mifos
 *
 * The MifosRequest may contain different kinds of requests
 * like requests for loan, savings, loan products, group information, branch information
 */
public class MifosAPIService implements MifosService {
    @Override
    public MifosResponse process(MifosRequest request) {
        // Here is the code provided by Swagger that made the API call to Mifos
        System.out.println("This is to print out the request content");

        /* Basically, the steps of processing a MifosRequest sent from NLP engine
            0. Setup the connection with the Fineract local instance
            1. Consume the request, extract the Intent in the request
            2. Generate a mapping between Intent and API call (maybe a XML file in the resource folder, use ClassLoader to load it)
            3. Find the specific API to call and get the response
            4. Wrap and generate it in the MifosResponse
                a. The MifosResponse still need further enhancement about its structure
        */
        return null;
    }
}
