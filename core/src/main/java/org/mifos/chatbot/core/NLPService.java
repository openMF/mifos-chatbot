package org.mifos.chatbot.core;

import org.mifos.chatbot.core.model.Intent;

/**
 * This interface will handle the OpenNLP engine currently
 * If there are other NLP engines, simply add more interfaces for additional NLP engines
 */
public interface NLPService {
    Intent[] recognize(String text);
}
