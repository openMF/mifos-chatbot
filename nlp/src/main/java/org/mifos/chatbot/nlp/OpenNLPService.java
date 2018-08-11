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
package org.mifos.chatbot.nlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.mifos.chatbot.core.NLPService;
import org.mifos.chatbot.core.model.Intent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OpenNLPService implements NLPService {

    Logger logger = LoggerFactory.getLogger(OpenNLPService.class);

    /**
     * This method is to recognise user's input to find out what is their intention
     *
     * Instead of finding a specific API to handle the result, the recognize function is used to narrow down the scope to several functions,
     * then apply further processing
     *
     * @param text This is the message of user input, it may contain several sentences about their commands
     * @return user's intention, basically what they really want to do
     *
     * @author Zhao Dingfan
     */
    @Override
    public Intent[] recognize(String text) {
        // TODO: provide confidence level for recognition result
        try {
            String[] sentences = detectSentence(text);
            List<Intent> resultIntents = new ArrayList<>();
            for(String sentence : sentences) {
                List<Intent> subResultIntents = new ArrayList<>();
                String[] tokens = tokenize(sentence);
                Span[] resultSpans = findName(tokens);
                for (Span span : resultSpans) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(tokens[span.getStart()]);
                    for(int i = span.getStart()+1 ; i < span.getEnd() ; i++) {
                        sb.append(" " + tokens[i]);
                    }
                    subResultIntents.add(new Intent(sb.toString()));
                }

                Long id = findId(tokens);
                String date = findDate(tokens);
                for(Intent intent : subResultIntents) {
                    intent.addParameter("ID", id);
                    intent.addParameter("Date", date);
                }
                resultIntents.addAll(subResultIntents);
            }

            Intent[] intents = new Intent[resultIntents.size()];
            for(int i = 0 ; i < resultIntents.size() ; i++) {
                intents[i] = resultIntents.get(i);
            }

            return intents;
        } catch (IOException e) {
            logger.error("Cannot read model information : ", e);
        }

        return null;
    }

    private String[] detectSentence(String paragraph) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("models/en-sent.bin");
        SentenceModel model = new SentenceModel(is);
        SentenceDetectorME sdetector = new SentenceDetectorME(model);

        String sentences[] = sdetector.sentDetect(paragraph);
        for(String sentence : sentences) {
            System.out.println(sentence);
        }
        is.close();

        return sentences;
    }

    private String[] tokenize(String sentence) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("models/en-token.bin");
        TokenizerModel model = new TokenizerModel(is);
        Tokenizer tokenizer = new TokenizerME(model);

        String tokens[] = tokenizer.tokenize(sentence);
        is.close();

        return tokens;
    }

    private Span[] findName(String[] tokens) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("models/en-ner-third-try.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(is);
        NameFinderME nameFinder = new NameFinderME(model);

        Span nameSpans[] = nameFinder.find(tokens);

        System.out.println(nameSpans.length + " spans found. ");
//        System.out.println(System.getProperty("user.dir"));

        is.close();
        return nameSpans;
    }

    /*
        However, only using en-ner-date model cannot detect date from input text
     */
    private String findDate(String[] tokenString) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("models/en-ner-date.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(is);
        NameFinderME dateFinder = new NameFinderME(model);

        Span[] dateSpans = dateFinder.find(tokenString);
        StringBuilder sb = new StringBuilder();
        for(Span span : dateSpans) {
            for(int i = span.getStart() ; i < span.getEnd() ; i++) {
                sb.append(" " + tokenString[i]);
            }
        }

        is.close();
        return sb.toString();
    }

    // TODO: please improve this

    /**
     * For ID number, we can use regex extraction to do the detection because of its simplicity of structure, which only contains number
     *
     * We have tokenize the string. Hence, what we have to do is to find out whether the token is full of number
     *
     * Default ID is 1
     *
     * @param tokens This param contains the series of tokens detected from tokenisation process
     * @return
     */
    private Long findId(String[] tokens) {
        Long id = 1L;
        for(String token: tokens) {
            try {
                id = Long.parseLong(token);
            } catch (NumberFormatException e) {
            }
        }

        return id;
    }
}
