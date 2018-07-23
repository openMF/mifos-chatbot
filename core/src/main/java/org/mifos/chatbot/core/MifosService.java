package org.mifos.chatbot.core;

import org.mifos.chatbot.core.model.MifosRequest;
import org.mifos.chatbot.core.model.MifosResponse;

/**
 * Connection with Mifos backend
 */
@Deprecated // TODO: maybe this is not needed
public interface MifosService {
    MifosResponse process(MifosRequest request);
}
