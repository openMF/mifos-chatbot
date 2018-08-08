package org.mifos.chatbot.core;

import org.mifos.chatbot.core.model.MifosRequest;
import org.mifos.chatbot.core.model.MifosResponse;

/**
 * This is not needed
 *
 * Connection with Mifos backend
 */
@Deprecated
public interface MifosService {
    MifosResponse process(MifosRequest request);
}
